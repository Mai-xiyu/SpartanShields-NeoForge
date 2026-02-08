package org.xiyu.spartanshieldsunofficial.item;

import net.minecraft.world.item.ItemStack;

public interface IItemPoweredFE {
    int receiveFE(ItemStack stack, int maxReceive, boolean simulate);

    int extractFE(ItemStack stack, int maxExtract, boolean simulate);

    int getFEStored(ItemStack stack);

    int getFECapacity(ItemStack stack);

    boolean canExtractFE(ItemStack stack);

    boolean canReceiveFE(ItemStack stack);
}
