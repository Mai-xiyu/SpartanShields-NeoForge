package org.xiyu.spartanshieldsunofficial.util;

import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * NeoForge {@link IEnergyStorage} 适配器。
 * <p>
 * 将 {@code receiveEnergy}/{@code extractEnergy}/{@code getEnergyStored} 等操作
 * 委托给 {@link IResourceType#getStored}/{@link IResourceType#setStored}。
 * </p>
 * <p>
 * 由 {@link org.xiyu.spartanshieldsunofficial.api.resource.AbstractEnergyStorage#onRegisterCapabilities}
 * 在注册 Capability 时自动创建，附属模组不需要知道它的存在。
 * </p>
 */
public class EnergyCapabilityAdapter implements IEnergyStorage {

    private final ItemStack stack;
    private final IResourceType resourceType;
    private final int capacity;
    private final int maxReceive;

    public EnergyCapabilityAdapter(ItemStack stack, IResourceType resourceType, int capacity, int maxReceive) {
        this.stack = stack;
        this.resourceType = resourceType;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int stored = this.resourceType.getStored(this.stack);
        int energyReceived = Math.min(this.capacity - stored, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            this.resourceType.setStored(this.stack, stored + energyReceived);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        // 盾牌不允许提取能量
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return this.resourceType.getStored(this.stack);
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
