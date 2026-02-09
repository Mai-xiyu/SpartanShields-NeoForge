package org.xiyu.spartanshieldsunofficial.api.resource;

import java.util.function.Supplier;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import org.xiyu.spartanshieldsunofficial.util.EnergyCapabilityAdapter;

/**
 * 基于 NeoForge Energy 体系的资源类型基类。
 * <p>
 * 继承自 {@link SimpleResourceType}，额外实现了
 * {@link IResourceType#onRegisterCapabilities} 方法：
 * 自动将盾牌注册到 {@code Capabilities.EnergyStorage.ITEM}，
 * 使其可被任何 FE 充电器（如 Mekanism 充能面板、Thermal 充能器等）识别和充电。
 * </p>
 *
 * <h3>使用示例</h3>
 * <pre>{@code
 * // 做一个显示为 "RF" 的能量盾牌（底层仍是 FE）
 * IResourceType rf = new AbstractEnergyStorage(
 *     ResourceLocation.fromNamespaceAndPath("thermal", "redstone_flux"),
 *     Component.literal("RF"),
 *     ModDataComponents.STORED_ENERGY,  // DeferredHolder IS-A Supplier
 *     0xCC4C4C
 * );
 * }</pre>
 */
public class AbstractEnergyStorage extends SimpleResourceType {

    public AbstractEnergyStorage(ResourceLocation id, Component displayName,
                                  DataComponentType<Integer> dataComponent, int barColor) {
        super(id, displayName, dataComponent, barColor);
    }

    /**
     * 接受 {@code Supplier<DataComponentType<Integer>>} 的构造器。
     * <p>
     * NeoForge 的 {@code DeferredHolder} 本身实现了 {@code Supplier}，可直接传入。
     * </p>
     *
     * @param id                    全局唯一 ID
     * @param displayName           显示名
     * @param dataComponentSupplier DataComponentType 的供应器（通常直接传入 DeferredHolder）
     * @param barColor              耐久条颜色
     */
    public AbstractEnergyStorage(ResourceLocation id, Component displayName,
                                  Supplier<DataComponentType<Integer>> dataComponentSupplier, int barColor) {
        super(id, displayName, dataComponentSupplier, barColor);
    }

    /**
     * 自动注册 NeoForge Energy Capability。
     * <p>
     * 将盾牌注册到 {@code Capabilities.EnergyStorage.ITEM}，
     * 使其可被所有兼容 FE 的模组设备识别。
     * 内部创建 {@code EnergyCapabilityAdapter}（实现 IEnergyStorage），
     * 将 receive/extract/getStored 等操作委托给 IResourceType。
     * </p>
     */
    @Override
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event, Item shield,
                                        int capacity, int maxReceive) {
        event.registerItem(
            Capabilities.EnergyStorage.ITEM,
            (stack, context) -> new EnergyCapabilityAdapter(stack, this, capacity, maxReceive),
            shield
        );
    }
}
