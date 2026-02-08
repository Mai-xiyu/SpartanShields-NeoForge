package org.xiyu.spartanshieldsunofficial.util;

import org.xiyu.spartanshieldsunofficial.item.IItemPoweredFE;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * Wrapper class that provides IEnergyStorage for items implementing IItemPoweredFE.
 * In NeoForge 1.21+, capabilities are registered via RegisterCapabilitiesEvent instead of ICapabilityProvider.
 */
/**
 * @deprecated 已被 {@link EnergyCapabilityAdapter} 取代。
 * 保留仅为了向后兼容现有 ModItems 中的物品注册。
 */
@Deprecated(forRemoval = true)
public class EnergyCapabilityProviderItem implements IEnergyStorage {
    private final ItemStack stack;
    private final IItemPoweredFE item;

    public EnergyCapabilityProviderItem(ItemStack stack, IItemPoweredFE item) {
        this.stack = stack;
        this.item = item;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.item.receiveFE(this.stack, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.item.extractFE(this.stack, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return this.item.getFEStored(this.stack);
    }

    @Override
    public int getMaxEnergyStored() {
        return this.item.getFECapacity(this.stack);
    }

    @Override
    public boolean canExtract() {
        return this.item.canExtractFE(this.stack);
    }

    @Override
    public boolean canReceive() {
        return this.item.canReceiveFE(this.stack);
    }
}
