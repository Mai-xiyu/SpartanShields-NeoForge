package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.world.item.ItemStack;

/**
 * 资源存储操作接口。
 * <p>
 * 附属模组通过 {@code instanceof IResourceStorage} 判断盾牌是否为资源供能盾牌，并进行操作。
 * 不关心底层是 FE 还是 Mana。
 * </p>
 */
public interface IResourceStorage {

    /** 获取该盾牌使用的资源类型 */
    IResourceType getResourceType();

    /** 获取最大容量 */
    int getCapacity();

    /** 获取最大接收速率 */
    int getMaxReceive();

    /** 接收资源，返回实际接收量 */
    int receive(ItemStack stack, int maxAmount, boolean simulate);

    /** 提取资源，返回实际提取量 */
    int extract(ItemStack stack, int maxAmount, boolean simulate);
}
