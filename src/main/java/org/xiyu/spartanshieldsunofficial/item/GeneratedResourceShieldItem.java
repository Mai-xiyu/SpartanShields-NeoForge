package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.api.resource.IResourceStorage;
import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;
import org.xiyu.spartanshieldsunofficial.api.shield.IShieldBlockHandler;
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldBuilder;
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldType;
import org.xiyu.spartanshieldsunofficial.client.ClientHelper;
import org.xiyu.spartanshieldsunofficial.config.Config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

/**
 * 由 {@link ShieldBuilder} 生成的资源供能盾牌 Item。
 * <p>
 * 当 Builder 配置了 {@code poweredBy}（无 {@code material}）时创建此类。
 * 实现 {@link IResourceStorage} 接口，支持通用资源读写。
 * 支持可叠加的格挡效果和自定义格挡处理器。
 * </p>
 */
public class GeneratedResourceShieldItem extends ShieldBaseItem implements IDamageShield, IResourceStorage {

    private final IResourceType resourceType;
    private final int energyCapacity;
    private final int maxEnergyReceive;
    private final List<ShieldBuilder.BlockEffectEntry> blockEffects;
    private final List<IShieldBlockHandler> blockHandlers;

    public GeneratedResourceShieldItem(IResourceType resourceType, int capacity, int maxReceive,
                                        ShieldType type,
                                        List<ShieldBuilder.BlockEffectEntry> blockEffects,
                                        List<IShieldBlockHandler> blockHandlers) {
        super(0, type == ShieldType.TOWER, new Item.Properties());
        this.resourceType = resourceType;
        this.energyCapacity = type == ShieldType.TOWER ? Mth.floor(capacity * 1.25f) : capacity;
        this.maxEnergyReceive = maxReceive;
        this.blockEffects = blockEffects;
        this.blockHandlers = blockHandlers;

        if (FMLEnvironment.dist.isClient()) {
            ClientHelper.registerPoweredShieldPropertyOverrides(this);
        }
    }

    // ===== IDamageShield =====

    @Override
    public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) {
        // 消耗能量
        int energyToUse = Mth.floor(damage);
        int currentEnergy = this.resourceType.getStored(shieldStack);
        int energyRemoved = Math.min(energyToUse, currentEnergy);
        currentEnergy -= energyRemoved;
        this.resourceType.setStored(shieldStack, currentEnergy);

        if (currentEnergy == 0) {
            Level level = player.level();
            player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
        }

        // 施加格挡效果
        if (attacker instanceof LivingEntity livingAttacker) {
            for (ShieldBuilder.BlockEffectEntry entry : this.blockEffects) {
                livingAttacker.addEffect(new MobEffectInstance(
                    entry.effect(), entry.ticks(), entry.amplifier(), false, true
                ));
            }
            for (IShieldBlockHandler handler : this.blockHandlers) {
                handler.onBlock(shieldStack, player, livingAttacker, damage);
            }
        }
    }

    // ===== IResourceStorage =====

    @Override
    public IResourceType getResourceType() {
        return this.resourceType;
    }

    @Override
    public int getCapacity() {
        return this.energyCapacity;
    }

    @Override
    public int getMaxReceive() {
        return this.maxEnergyReceive;
    }

    @Override
    public int receive(ItemStack stack, int maxAmount, boolean simulate) {
        int stored = this.resourceType.getStored(stack);
        int received = Math.min(this.energyCapacity - stored, Math.min(this.maxEnergyReceive, maxAmount));
        if (!simulate) {
            this.resourceType.setStored(stack, stored + received);
        }
        return received;
    }

    @Override
    public int extract(ItemStack stack, int maxAmount, boolean simulate) {
        return 0; // 盾牌不允许提取
    }

    // ===== 显示覆写 =====

    @Override
    public void setDamage(@NotNull ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        int energy = this.resourceType.getStored(stack);
        return Math.round(13.0f * ((float) energy) / (float) this.energyCapacity);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        int energy = this.resourceType.getStored(stack);
        return energy < this.energyCapacity;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return this.resourceType.getBarColor();
    }

    @Override
    public int getMaxDamage(@NotNull ItemStack stack) {
        return this.energyCapacity;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context,
                                 @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        int stored = this.resourceType.getStored(stack);
        tooltip.add(this.resourceType.formatCapacityTooltip(stored, this.energyCapacity));
        tooltip.add(this.resourceType.formatChargeRateTooltip(this.maxEnergyReceive));

        int costPerDamage = Mth.floor(Config.INSTANCE.damageToFEMultiplier.get() * 2);
        tooltip.add(this.resourceType.formatPerDamageTooltip(costPerDamage));

        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".fe_shield.desc"));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return this.resourceType.getStored(stack) > 0 ? UseAnim.BLOCK : UseAnim.NONE;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        ItemStack stack = player.getItemInHand(hand);
        if (this.resourceType.getStored(stack) > 0) {
            return InteractionResultHolder.consume(stack);
        } else {
            if (level.isClientSide) {
                player.sendSystemMessage(Component.literal(ChatFormatting.YELLOW
                    + I18n.get("message." + ModSpartanShields.ID + ".powered_shield_block_fail",
                        stack.getHoverName().getString())));
            }
            return InteractionResultHolder.fail(stack);
        }
    }

    /** 获取格挡效果列表 */
    public List<ShieldBuilder.BlockEffectEntry> getBlockEffects() {
        return this.blockEffects;
    }

    /** 获取格挡处理器列表 */
    public List<IShieldBlockHandler> getBlockHandlers() {
        return this.blockHandlers;
    }
}
