package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.api.resource.IResourceStorage;
import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;
import org.xiyu.spartanshieldsunofficial.api.resource.ResourceRegistry;
import org.xiyu.spartanshieldsunofficial.client.ClientHelper;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.util.PowerUnit;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class FEPoweredShieldItem extends ShieldBaseItem implements IDamageShield, IItemPoweredFE, IResourceStorage {
    protected int energyCapacity;
    protected int maxEnergyReceive;
    protected String modName;
    protected PowerUnit preferredEnergyUnit;
    protected IResourceType resourceType;

    public FEPoweredShieldItem(int capacity, int maxReceive, String modName, PowerUnit preferredUnit, boolean isTowerShieldIn, Item.Properties prop) {
        super(0, isTowerShieldIn, prop);
        this.energyCapacity = isTowerShieldIn ? Mth.floor(capacity * 1.25f) : capacity;
        this.maxEnergyReceive = maxReceive;
        this.modName = modName;
        this.preferredEnergyUnit = preferredUnit;
        // 根据 PowerUnit 确定对应的 IResourceType
        this.resourceType = (preferredUnit == PowerUnit.MicroInfinity)
            ? ResourceRegistry.MICRO_INFINITY
            : ResourceRegistry.ENERGY;

        if (FMLEnvironment.dist.isClient())
            ClientHelper.registerPoweredShieldPropertyOverrides(this);
    }

    // Note: setDamage override removed - Forge 1.21.1 may not have this method to override
    // The energy-based damage system needs to work differently
    public void setDamageValue(@NotNull ItemStack stack, int damage) {
        // Keep damage at 0 for energy-powered shields
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        int energy = this.getStoredEnergy(stack);
        return Math.round(13.0f * ((float) energy) / (float) this.energyCapacity);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        int energy = this.getStoredEnergy(stack);
        return energy < this.energyCapacity;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x69B3FF;
    }

    /**
     * Returns the packed int RGB value used to render the durability bar in the GUI.
     * Defaults to a value based on the hue scaled as the damage decreases, but can be overriden.
     *
     * @param stack Stack to get durability from
     * @return A packed RGB value for the durability colour (0x00RRGGBB)
     */
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0x69B3FF;
    }

    @Override
    public int getMaxDamage(@NotNull ItemStack stack) {
        return this.energyCapacity;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        LocalizedNumberFormatter formatter = NumberFormatter.withLocale(Minecraft.getInstance().getLanguageManager().getJavaLocale());
        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + this.preferredEnergyUnit.getCapacityTranslationKey(), formatter.format(Mth.floor(this.getFEStored(stack) * this.preferredEnergyUnit.getEnergyScaleToFE())).toString(), formatter.format(Mth.floor(this.getFECapacity(stack) * this.preferredEnergyUnit.getEnergyScaleToFE())).toString()));
        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + this.preferredEnergyUnit.getEnergyChargeRateTranslationKey(), formatter.format(Mth.floor(this.maxEnergyReceive * this.preferredEnergyUnit.getEnergyScaleToFE())).toString()));
        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + this.preferredEnergyUnit.getEnergyPerDamageTranslationKey(), formatter.format(Mth.floor(Config.INSTANCE.damageToFEMultiplier.get() * 2 * this.preferredEnergyUnit.getEnergyScaleToFE())).toString()));
        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + "fe_shield.desc"));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        if (this.getFEStored(stack) > 0)
            return UseAnim.BLOCK;
        else
            return UseAnim.NONE;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        ItemStack stack = player.getItemInHand(hand);
        if (this.getFEStored(stack) > 0)
            return InteractionResultHolder.consume(stack);
        else {
            if (level.isClientSide)
                player.sendSystemMessage(Component.literal(ChatFormatting.YELLOW
                        + I18n.get("message." + ModSpartanShields.ID + ".powered_shield_block_fail", stack.getHoverName().getString())));
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) {
        int energyToUse = Mth.floor(damage);

        // Remove FE from the shield to absorb the damage.
        int currentEnergy = this.getStoredEnergy(shieldStack);
        int energyRemoved = Math.min(energyToUse, currentEnergy);

        currentEnergy -= energyRemoved;
        this.setStoredEnergy(shieldStack, currentEnergy);

        if (currentEnergy == 0) {
            Level level = player.level();
            player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
        }
    }

    public FEPoweredShieldItem setCapacity(int capacity) {
        this.energyCapacity = capacity;
        return this;
    }

    public FEPoweredShieldItem setMaxReceive(int maxReceive) {
        this.maxEnergyReceive = maxReceive;
        return this;
    }

    /* IItemPoweredFE */
    @Override
    public int receiveFE(ItemStack container, int maxReceive, boolean simulate) {
        int energy = this.getStoredEnergy(container);
        int energyReceived = Math.min(this.energyCapacity - energy, Math.min(this.maxEnergyReceive, maxReceive));

        if (!simulate) {
            energy += energyReceived;
            this.setStoredEnergy(container, energy);
        }
        return energyReceived;
    }

    @Override
    public int extractFE(ItemStack container, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getFEStored(ItemStack container) {
        return ModDataComponents.getStoredEnergy(container);
    }

    /**
     * Gets the stored energy from an ItemStack
     *
     * @param stack the stack to get energy from
     * @return the stored energy amount
     */
    public int getStoredEnergy(ItemStack stack) {
        return this.getFEStored(stack);
    }

    /**
     * Sets the stored energy on an ItemStack
     *
     * @param stack  the stack to set energy on
     * @param energy the amount of energy to set
     */
    public void setStoredEnergy(ItemStack stack, int energy) {
        ModDataComponents.setStoredEnergy(stack, energy);
    }

    @Override
    public int getFECapacity(ItemStack container) {
        return this.energyCapacity;
    }

    @Override
    public boolean canExtractFE(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canReceiveFE(ItemStack stack) {
        return true;
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
        return this.receiveFE(stack, maxAmount, simulate);
    }

    @Override
    public int extract(ItemStack stack, int maxAmount, boolean simulate) {
        return this.extractFE(stack, maxAmount, simulate);
    }
}
