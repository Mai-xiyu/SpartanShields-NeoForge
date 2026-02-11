package org.xiyu.spartanshieldsunofficial.network;

import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModEnchantments;
import org.xiyu.spartanshieldsunofficial.init.ModSounds;
import org.xiyu.spartanshieldsunofficial.init.ModStats;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.tags.ModItemTags;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class ShieldBashPacket {
    private final InteractionHand hand;
    private final int entityId;
    private final boolean attackEntity;
    
    public ShieldBashPacket(InteractionHand hand, int entityId, boolean attackEntity) {
        this.hand = hand;
        this.entityId = entityId;
        this.attackEntity = attackEntity;
    }
    
    public static void encode(ShieldBashPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.hand == InteractionHand.MAIN_HAND);
        buf.writeInt(packet.entityId);
        buf.writeBoolean(packet.attackEntity);
    }
    
    public static ShieldBashPacket decode(FriendlyByteBuf buf) {
        InteractionHand hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        int entityId = buf.readInt();
        boolean attackEntity = buf.readBoolean();
        return new ShieldBashPacket(hand, entityId, attackEntity);
    }

    /**
     * 判断盾牌是否允许猛击：通过 Tag 或通过 API 的 bashable 标志。
     */
    private static boolean isBashAllowed(ItemStack stack) {
        return stack.is(ModItemTags.SHIELDS_WITH_BASH)
            || (stack.getItem() instanceof ShieldBaseItem shield && shield.isBashable());
    }

    public static void handle(final ShieldBashPacket packet, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() ->
        {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            
            Entity victim = player.level().getEntity(packet.entityId);

            if (player.isBlocking()) {
                ItemStack shieldStack = player.getItemInHand(packet.hand);
                boolean isTowerShield = shieldStack.is(ModItemTags.TOWER_SHIELDS);

                if (!shieldStack.isEmpty() && !player.getCooldowns().isOnCooldown(shieldStack.getItem()) &&
                        isBashAllowed(player.getUseItem()) && player.getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
                    if (packet.attackEntity && victim instanceof LivingEntity) {
                        // Deal minimal damage and knock back foes
                        int knockLvl = getEnchantmentLevel(player, shieldStack, Enchantments.KNOCKBACK);

                        // Decide between increased knockback or bashing multiple foes similar to sweeping with a sword (currently implemented)
                        if (isTowerShield) {
                            double reach = player.entityInteractionRange();
                            for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(1.0d, 0.25d, 1.0d),
                                    (target) -> target != player && target != victim && !target.isAlliedTo(player) && (!(target instanceof ArmorStand) || !((ArmorStand) target).isMarker()) && player.distanceToSqr(target) < reach * reach)) {
                                bashEntity(entity, player, shieldStack, knockLvl, packet.hand);
                            }
                        }

                        boolean powerfulBash = bashEntity(victim, player, shieldStack, knockLvl, packet.hand);
                        // Increase the pitch whenever the bash damage is higher
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.SHIELD_BASH_HIT.get(), player.getSoundSource(), 1.0F, powerfulBash ? 2.0f : 1.0f);
                        player.crit(victim);

                        // Add to shield bash hits stat
                        player.awardStat(ModStats.SHIELD_BASH_HITS.get());
                    } else {
                        // ...swing and a miss...
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.SHIELD_BASH_MISS.get(), player.getSoundSource(), 0.5f, 0.01f);
                    }
                    player.stopUsingItem();
                    player.getCooldowns().addCooldown(shieldStack.getItem(), isTowerShield ? Config.INSTANCE.cooldownTowerShieldBash.get() : Config.INSTANCE.cooldownShieldBash.get());
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    private static int getEnchantmentLevel(Player player, ItemStack stack, net.minecraft.resources.ResourceKey<Enchantment> key) {
        Holder<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
        return EnchantmentHelper.getItemEnchantmentLevel(holder, stack);
    }

    private static boolean bashEntity(Entity targetEntity, Player player, ItemStack shieldStack, int knockbackLevel, InteractionHand hand) {
        targetEntity.invulnerableTime = 0;
        ((LivingEntity) targetEntity).knockback(1.0f + (knockbackLevel), Mth.sin(player.getYRot() * 0.017453292F), -Mth.cos(player.getYRot() * 0.017453292F));

        float bashDamage = 1.0f;
        // Apply the Payback damage bonus if necessary
        int paybackLevel = getEnchantmentLevel(player, shieldStack, ModEnchantments.PAYBACK);
        if (paybackLevel > 0) {
            bashDamage += ModDataComponents.getPaybackDamage(shieldStack);
            ModDataComponents.setPaybackDamage(shieldStack, 0.0f);
        }

        // Finally deal damage.
        targetEntity.hurt(player.damageSources().playerAttack(player), bashDamage);
        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        shieldStack.hurtAndBreak(5, player, slot);

        // Set foes on fire when hit with the Shield Bash
        int firebrandLvl = getEnchantmentLevel(player, shieldStack, ModEnchantments.FIREBRAND);
        if (firebrandLvl > 0)
            targetEntity.setRemainingFireTicks(firebrandLvl * 5 * 20); // Convert seconds to ticks

        return bashDamage > 1.0f;
    }
    
    public InteractionHand getHand() { return hand; }
    public int getEntityId() { return entityId; }
    public boolean isAttackEntity() { return attackEntity; }
}