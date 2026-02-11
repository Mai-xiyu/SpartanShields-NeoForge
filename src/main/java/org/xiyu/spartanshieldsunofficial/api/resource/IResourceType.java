package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

/**
 * 资源类型定义 — 整个 API 的核心扩展点。
 * <p>
 * Forge 1.21.1: Simplified to use NBT-based storage instead of DataComponentType
 * </p>
 */
public interface IResourceType {

    /** 全局唯一 ID，如 "forge:energy", "botania:mana" */
    ResourceLocation getId();

    /** 本地化显示名，如 "FE", "Mana" */
    Component getDisplayName();

    /** 从 ItemStack 读取当前存储量 */
    int getStored(ItemStack stack);

    /** 向 ItemStack 写入存储量 */
    void setStored(ItemStack stack, int amount);

    /** 格式化容量显示，如 "50,000 / 100,000 FE" */
    Component formatCapacityTooltip(int stored, int capacity);

    /** 格式化充能速率显示，如 "1,000 FE/t" */
    Component formatChargeRateTooltip(int maxReceive);

    /** 格式化每点伤害消耗显示 */
    Component formatPerDamageTooltip(int costPerDamage);

    /** 物品耐久条颜色 (RGB) */
    int getBarColor();

    /** Capability 注册回调（可选） */
    default void onRegisterCapabilities(RegisterCapabilitiesEvent event, Item shield, int capacity, int maxReceive) {}
}
