package org.xiyu.spartanshieldsunofficial.item;

import net.minecraft.world.item.ItemStack;

/**
 * @deprecated 已被 {@link org.xiyu.spartanshieldsunofficial.api.resource.IResourceStorage} 取代。
 * 请使用 {@code IResourceStorage} 接口进行资源存储操作。
 */
@Deprecated(forRemoval = true)
public interface IItemPoweredFE {
    int receiveFE(ItemStack stack, int maxReceive, boolean simulate);

    int extractFE(ItemStack stack, int maxExtract, boolean simulate);

    int getFEStored(ItemStack stack);

    int getFECapacity(ItemStack stack);

    boolean canExtractFE(ItemStack stack);

    boolean canReceiveFE(ItemStack stack);
}
