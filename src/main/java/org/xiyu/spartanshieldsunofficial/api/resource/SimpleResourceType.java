package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;

/**
 * {@link IResourceType} 的通用实现基类。
 * <p>
 * Forge 1.21.1: Uses NBT-based storage instead of DataComponentType
 * </p>
 */
public class SimpleResourceType implements IResourceType {

    private final ResourceLocation id;
    private final Component displayName;
    private final int barColor;
    private final String nbtKey;

    public SimpleResourceType(ResourceLocation id, Component displayName, String nbtKey, int barColor) {
        this.id = id;
        this.displayName = displayName;
        this.nbtKey = nbtKey;
        this.barColor = barColor;
    }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public Component getDisplayName() { return displayName; }

    @Override
    public int getBarColor() { return barColor; }

    @Override
    public int getStored(ItemStack stack) {
        return ModDataComponents.getStoredEnergy(stack);
    }

    @Override
    public void setStored(ItemStack stack, int amount) {
        ModDataComponents.setStoredEnergy(stack, Math.max(0, amount));
    }

    @Override
    public Component formatCapacityTooltip(int stored, int capacity) {
        return Component.literal(String.format("%,d / %,d ", stored, capacity))
            .append(displayName);
    }

    @Override
    public Component formatChargeRateTooltip(int maxReceive) {
        return Component.literal(String.format("%,d ", maxReceive))
            .append(displayName)
            .append(Component.literal("/t"));
    }

    @Override
    public Component formatPerDamageTooltip(int costPerDamage) {
        return Component.literal(String.format("%,d ", costPerDamage))
            .append(displayName)
            .append(Component.literal("/hit"));
    }
}
