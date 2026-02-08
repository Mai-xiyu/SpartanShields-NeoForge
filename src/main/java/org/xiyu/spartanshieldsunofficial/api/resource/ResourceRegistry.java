package org.xiyu.spartanshieldsunofficial.api.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;

/**
 * 资源类型注册表。
 * <p>
 * 预置 2 种资源类型（Energy / µI），其余由附属模组按需注册。
 * </p>
 * <p>
 * <b>防冲突机制</b>：如果某个 ID 已被注册，{@link #register(IResourceType)} 会立即抛出
 * {@link IllegalArgumentException}，附属模组开发者可以在启动日志中立即看到冲突原因。
 * </p>
 */
public final class ResourceRegistry {

    private static final Map<ResourceLocation, IResourceType> REGISTRY = new LinkedHashMap<>();

    // ===== 预置资源类型 =====

    /** NeoForge Energy (FE) — NeoForge 标准能量单位 */
    public static final IResourceType ENERGY;

    /** Micro Infinity (µI) — EnderIO 能量单位（底层复用 NeoForge Energy，仅 Tooltip 显示不同） */
    public static final IResourceType MICRO_INFINITY;

    // ===== 注册与查询 =====

    /**
     * 注册新资源类型。
     *
     * @param type 要注册的资源类型（ID 必须全局唯一）
     * @throws IllegalArgumentException 当 type.getId() 已被注册时
     * @throws NullPointerException 当 type 或 type.getId() 为 null 时
     */
    public static void register(IResourceType type) {
        Objects.requireNonNull(type, "ResourceType cannot be null");
        Objects.requireNonNull(type.getId(), "ResourceType ID cannot be null");
        if (REGISTRY.containsKey(type.getId())) {
            throw new IllegalArgumentException(
                "Resource type '" + type.getId() + "' is already registered! " +
                "Existing: " + REGISTRY.get(type.getId()).getClass().getName() + ", " +
                "Duplicate: " + type.getClass().getName()
            );
        }
        REGISTRY.put(type.getId(), type);
    }

    /** 根据 ID 查询 */
    public static Optional<IResourceType> get(ResourceLocation id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    /** 获取所有已注册类型（不可变视图） */
    public static Collection<IResourceType> getAll() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    // ===== 预置类型初始化 =====
    static {
        ENERGY = new AbstractEnergyStorage(
            ResourceLocation.fromNamespaceAndPath("neoforge", "energy"),
            Component.literal("FE"),
            ModDataComponents.STORED_ENERGY.get(),
            0x69B3FF
        );
        MICRO_INFINITY = new AbstractEnergyStorage(
            ResourceLocation.fromNamespaceAndPath("enderio", "micro_infinity"),
            Component.literal("µI"),
            ModDataComponents.STORED_ENERGY.get(),
            0x4DA24B
        );
        register(ENERGY);
        register(MICRO_INFINITY);
    }
}
