package org.xiyu.spartanshieldsunofficial.init;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Enchantment keys for data-driven enchantments in 1.21+
 * Actual enchantment definitions are in data/spartanshieldsunofficial/enchantment/
 */
public class ModEnchantments {
    public static final ResourceKey<Enchantment> SPIKES = createKey("spikes");
    public static final ResourceKey<Enchantment> FIREBRAND = createKey("firebrand");
    public static final ResourceKey<Enchantment> PAYBACK = createKey("payback");

    private static ResourceKey<Enchantment> createKey(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, name));
    }
}
