package org.xiyu.spartanshieldsunofficial.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * Helper methods for shield data storage using CustomData (Forge 1.21.1)
 * Note: In 1.21, ItemStack.getTag()/hasTag() were removed
 */
public class ModDataComponents {
    // NBT Keys
    public static final String NBT_PAYBACK_DAMAGE = "PaybackDamage";
    public static final String NBT_STORED_ENERGY = "StoredEnergy";

    private static CompoundTag getCustomTag(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData != null ? customData.copyTag() : new CompoundTag();
    }

    private static void setCustomTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static float getPaybackDamage(ItemStack stack) {
        CompoundTag tag = getCustomTag(stack);
        return tag.contains(NBT_PAYBACK_DAMAGE) ? tag.getFloat(NBT_PAYBACK_DAMAGE) : 0.0f;
    }

    public static void setPaybackDamage(ItemStack stack, float damage) {
        CompoundTag tag = getCustomTag(stack);
        tag.putFloat(NBT_PAYBACK_DAMAGE, damage);
        setCustomTag(stack, tag);
    }

    public static int getStoredEnergy(ItemStack stack) {
        CompoundTag tag = getCustomTag(stack);
        return tag.contains(NBT_STORED_ENERGY) ? tag.getInt(NBT_STORED_ENERGY) : 0;
    }

    public static void setStoredEnergy(ItemStack stack, int energy) {
        CompoundTag tag = getCustomTag(stack);
        tag.putInt(NBT_STORED_ENERGY, energy);
        setCustomTag(stack, tag);
    }
}
