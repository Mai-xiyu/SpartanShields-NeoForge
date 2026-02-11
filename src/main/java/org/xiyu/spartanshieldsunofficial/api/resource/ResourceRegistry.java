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
 * Forge 1.21.1: Simplified to use NBT-based storage
 * </p>
 */
public final class ResourceRegistry {

    private static final Map<ResourceLocation, IResourceType> REGISTRY = new LinkedHashMap<>();

    /** NeoForge Energy (FE) */
    public static final IResourceType ENERGY;

    /** Micro Infinity (µI) — EnderIO */
    public static final IResourceType MICRO_INFINITY;

    public static void register(IResourceType type) {
        Objects.requireNonNull(type, "ResourceType cannot be null");
        Objects.requireNonNull(type.getId(), "ResourceType ID cannot be null");
        if (REGISTRY.containsKey(type.getId())) {
            throw new IllegalArgumentException(
                "Resource type '" + type.getId() + "' is already registered!"
            );
        }
        REGISTRY.put(type.getId(), type);
    }

    public static Optional<IResourceType> get(ResourceLocation id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    public static Collection<IResourceType> getAll() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    static {
        ENERGY = new AbstractEnergyStorage(
            ResourceLocation.tryBuild("forge", "energy"),
            Component.literal("FE"),
            ModDataComponents.NBT_STORED_ENERGY,
            0x69B3FF
        );
        MICRO_INFINITY = new AbstractEnergyStorage(
            ResourceLocation.tryBuild("enderio", "micro_infinity"),
            Component.literal("µI"),
            ModDataComponents.NBT_STORED_ENERGY,
            0x4DA24B
        );
        register(ENERGY);
        register(MICRO_INFINITY);
    }
}
