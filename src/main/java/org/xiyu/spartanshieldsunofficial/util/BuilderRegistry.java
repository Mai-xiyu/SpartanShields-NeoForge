package org.xiyu.spartanshieldsunofficial.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;

import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;

/**
 * 内部注册表，收集所有通过 {@code ShieldBuilder.poweredBy()} 创建的盾牌。
 * <p>
 * 在 {@code ShieldBuilder.build()} 时将信息存入此处，
 * 在 {@code RegisterCapabilitiesEvent} 时由 {@code CapabilityEventHandler} 统一遍历并注册 Capability。
 * </p>
 */
public final class BuilderRegistry {

    /**
     * 能量盾牌条目记录
     */
    public record PoweredShieldEntry(
        Supplier<? extends Item> itemSupplier,
        IResourceType resourceType,
        int capacity,
        int maxReceive
    ) {}

    private static final List<PoweredShieldEntry> ENTRIES = new ArrayList<>();

    /**
     * 注册一个通过 Builder 创建的能量盾牌。
     * 由 {@code ShieldBuilder.build()} 内部调用。
     */
    public static void registerPoweredShield(Supplier<? extends Item> supplier,
                                              IResourceType type, int capacity, int maxReceive) {
        ENTRIES.add(new PoweredShieldEntry(supplier, type, capacity, maxReceive));
    }

    /**
     * 获取所有已注册的能量盾牌条目（不可变视图）。
     * 由 {@code CapabilityEventHandler} 在 {@code RegisterCapabilitiesEvent} 时调用。
     */
    public static List<PoweredShieldEntry> getRegisteredShields() {
        return Collections.unmodifiableList(ENTRIES);
    }
}
