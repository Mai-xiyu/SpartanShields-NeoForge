package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * 资源类型定义 — 整个 API 的核心扩展点。
 * <p>
 * 每种"能量/魔力/应力"都是一个 {@code IResourceType} 实例。
 * 附属模组通过实现此接口并注册到 {@link ResourceRegistry} 来接入新的资源类型。
 * </p>
 *
 * <h3>关于 {@link #getDataComponent()}</h3>
 * 在 Minecraft 1.21+ 中，客户端（Tooltip 渲染、耐久条显示）<b>无法直接读取服务端的 NBT</b>。
 * 数据必须通过 {@code DataComponent} 系统才能正确同步到客户端。
 * 因此每个 {@code IResourceType} 必须声明它关联的 {@code DataComponentType<?>}，
 * 主模组的通用 Tooltip 渲染器会通过 {@code stack.has(type.getDataComponent())} 来安全地读取数据。
 */
public interface IResourceType {

    /** 全局唯一 ID，如 "neoforge:energy", "botania:mana", "create:stress" */
    ResourceLocation getId();

    /** 本地化显示名，如 "FE", "Mana", "SU" */
    Component getDisplayName();

    // ===== Data Component（1.21+ 同步关键）=====

    /**
     * 返回该资源类型在 ItemStack 上使用的 DataComponentType。
     * <p>
     * 主模组的通用 Tooltip 渲染器和耐久条显示会调用此方法来读取/检测数据。
     * <b>必须使用 DataComponent 来存储</b>，否则客户端无法同步，Tooltip 会显示错误值。
     * </p>
     *
     * @return 该资源关联的 DataComponentType（通常是 {@code DataComponentType<Integer>}）
     */
    DataComponentType<?> getDataComponent();

    // ===== 存储 =====

    /**
     * 从 ItemStack 读取当前存储量。
     * <p>
     * <b>实现要求</b>：底层必须使用 {@link #getDataComponent()} 返回的 Component 来读取。
     * 典型实现：{@code stack.getOrDefault(getDataComponent(), 0)}
     * </p>
     */
    int getStored(ItemStack stack);

    /**
     * 向 ItemStack 写入存储量。
     * <p>
     * <b>实现要求</b>：底层必须使用 {@link #getDataComponent()} 返回的 Component 来写入。
     * 典型实现：{@code stack.set(getDataComponent(), amount)}
     * </p>
     */
    void setStored(ItemStack stack, int amount);

    // ===== Tooltip =====

    /**
     * 格式化容量显示，如 "50,000 / 100,000 FE" 或 "800 / 1,000 Mana"
     */
    Component formatCapacityTooltip(int stored, int capacity);

    /**
     * 格式化充能速率显示，如 "1,000 FE/t" 或 "10 Mana/s"
     */
    Component formatChargeRateTooltip(int maxReceive);

    /**
     * 格式化每点伤害消耗显示，如 "200 FE/伤害" 或 "5 Mana/伤害"
     */
    Component formatPerDamageTooltip(int costPerDamage);

    // ===== 显示 =====

    /** 物品耐久条颜色 (RGB, 如 0x69B3FF) */
    int getBarColor();

    // ===== Capability 集成（可选）=====

    /**
     * 当盾牌注册时调用，允许资源类型挂载对应的 NeoForge Capability。
     * <p>
     * 例如 Energy 类型在这里通过 {@code Capabilities.EnergyStorage.ITEM} 挂载 {@code IEnergyStorage}。
     * 非 NeoForge 体系的资源（如 Botania Mana）可以空实现。
     * </p>
     * <p>
     * <b>附属模组无需手动调用此方法</b>。主模组在 {@code RegisterCapabilitiesEvent} 时
     * 会自动扫描所有通过 ShieldBuilder 创建的能量盾牌，并调用对应 IResourceType 的此方法。
     * </p>
     *
     * @param event    NeoForge 的 RegisterCapabilitiesEvent
     * @param shield   盾牌物品实例
     * @param capacity 该盾牌的资源容量
     * @param maxReceive 该盾牌的最大接收速率
     */
    default void onRegisterCapabilities(RegisterCapabilitiesEvent event, Item shield, int capacity, int maxReceive) {}
}
