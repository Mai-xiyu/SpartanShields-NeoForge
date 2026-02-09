package org.xiyu.spartanshieldsunofficial.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.item.Item;

import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;

/**
 * 内部注册表，收集所有通过 {@code ShieldBuilder.poweredBy()} 创建的盾牌。
 * <p>
 * Item 在被 {@code DeferredRegister} 构造时自注册到此处（注册表冻结之前），
 * 在 {@code RegisterCapabilitiesEvent} 时由 {@code CapabilityEventHandler} 统一遍历并注册 Capability。
 * </p>
 */
public final class BuilderRegistry {

    /**
     * 能量盾牌条目记录
     *
     * @param shield       已创建的 Item 实例（由 DeferredRegister 构造，注册表冻结前）
     * @param resourceType 资源类型
     * @param capacity     容量
     * @param maxReceive   最大充能速率
     */
    public record PoweredShieldEntry(
        Item shield,
        IResourceType resourceType,
        int capacity,
        int maxReceive
    ) {}

    private static final List<PoweredShieldEntry> ENTRIES = new ArrayList<>();

    /**
     * 注册一个通过 Builder 创建的能量盾牌。
     * <p>
     * 由 {@code GeneratedResourceShieldItem} 构造器内部调用，
     * 此时 Item 正在被 {@code DeferredRegister} 创建，注册表尚未冻结。
     * </p>
     */
    public static void registerPoweredShield(Item shield,
                                              IResourceType type, int capacity, int maxReceive) {
        ENTRIES.add(new PoweredShieldEntry(shield, type, capacity, maxReceive));
    }

    /**
     * 获取所有已注册的能量盾牌条目（不可变视图）。
     * 由 {@code CapabilityEventHandler} 在 {@code RegisterCapabilitiesEvent} 时调用。
     */
    public static List<PoweredShieldEntry> getRegisteredShields() {
        return Collections.unmodifiableList(ENTRIES);
    }
}
