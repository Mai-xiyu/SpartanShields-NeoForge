package org.xiyu.spartanshieldsunofficial.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

/**
 * Constants for enchantment-related NBT/Data storage keys
 */
public class EnchantmentConstants {
    // Payback enchantment
    public static final String NBT_PAYBACK_DMG = "PaybackDamage";

    // Maximum damage capacity per enchantment level
    public static float getPaybackMaxDamageCapacity(int level) {
        return 2.0f * level;
    }

    // Ratio of damage absorbed for Payback
    public static float getPaybackAbsorbedDamageRatio() {
        return 0.5f;
    }

    /**
     * Gets the level of an enchantment on an ItemStack using Level for registry access
     *
     * @param level          the world level for registry access
     * @param stack          the item stack to check
     * @param enchantmentKey the ResourceKey of the enchantment
     * @return the enchantment level, or 0 if not present
     */
    public static int getEnchantmentLevel(Level level, ItemStack stack, ResourceKey<Enchantment> enchantmentKey) {
        if (level == null || stack.isEmpty()) return 0;

        var registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var enchantmentOpt = registry.get(enchantmentKey);

        return enchantmentOpt.map(enchantmentReference -> EnchantmentHelper.getItemEnchantmentLevel(enchantmentReference, stack)).orElse(0);
    }
}
