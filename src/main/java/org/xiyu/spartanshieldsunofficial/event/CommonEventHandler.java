package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModEnchantments;
import org.xiyu.spartanshieldsunofficial.init.ModSounds;
import org.xiyu.spartanshieldsunofficial.item.IDamageShield;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.util.EnchantmentConstants;

import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = org.xiyu.spartanshieldsunofficial.ModSpartanShields.ID)
public class CommonEventHandler {
    // Attack Event - Handles damage and enchantment triggering for shields.
    @SubscribeEvent
    public static void attackEvent(LivingDamageEvent ev) {
        float damage = ev.getAmount();

        if (ev.getEntity() instanceof Player player) {
            Entity directEntity = ev.getSource().getDirectEntity();

            // Check for entity causing damage
            if (!player.getUseItem().isEmpty() && player.getUseItem().getItem() instanceof ShieldBaseItem && ev.getSource().getDirectEntity() != null) {
                ItemStack activeStack = player.getUseItem();
                Entity attacker = ev.getSource().getEntity();

                // Handle Spikes enchantment
                int spikesLevel = EnchantmentHelper.getItemEnchantmentLevel(getEnchantmentHolder(player, ModEnchantments.SPIKES), activeStack);
                if (spikesLevel > 0 && attacker instanceof LivingEntity livingAttacker) {
                    handleSpikesEnchantment(player, livingAttacker, spikesLevel);
                }

                // Handle Payback enchantment (absorb damage for later release)
                int paybackLevel = EnchantmentHelper.getItemEnchantmentLevel(getEnchantmentHolder(player, ModEnchantments.PAYBACK), activeStack);
                if (paybackLevel > 0) {
                    handlePaybackEnchantment(player, activeStack, damage, paybackLevel);
                }

                // Copy of Player.damageShield() (Allowing for custom shields to take damage)
                if (damage >= 3.0F && !activeStack.isEmpty() && activeStack.getItem() instanceof IDamageShield && directEntity instanceof LivingEntity) {
                    ((IDamageShield) activeStack.getItem()).damageShield(activeStack, player, directEntity, damage);
                }
            }
        }
    }

    private static Holder<Enchantment> getEnchantmentHolder(Player player, net.minecraft.resources.ResourceKey<Enchantment> key) {
        return player.level().registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getOrThrow(key);
    }

    private static void handleSpikesEnchantment(Player user, LivingEntity attacker, int level) {
        // Deal damage back to attacker
        float spikeDamage = 1.0f + (level * 0.5f);
        attacker.hurt(user.damageSources().thorns(user), spikeDamage);
    }

    private static void handlePaybackEnchantment(Player user, ItemStack activeStack, float damage, int level) {
        float currentDmg = ModDataComponents.getPaybackDamage(activeStack);
        float maxDmg = EnchantmentConstants.getPaybackMaxDamageCapacity(level);
        float absorbedDmg = damage * EnchantmentConstants.getPaybackAbsorbedDamageRatio();

        currentDmg = Mth.clamp(currentDmg + absorbedDmg, 0.0f, maxDmg);
        ModDataComponents.setPaybackDamage(activeStack, currentDmg);

        // Let the player know that the shield is at maximum damage capacity
        if (currentDmg >= maxDmg) {
            user.level().playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.SHIELD_PAYBACK_CHARGE.get(), user.getSoundSource(), 0.5f, 2.0f);
        }
    }
}
