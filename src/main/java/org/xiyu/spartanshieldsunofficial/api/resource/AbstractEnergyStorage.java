package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

/**
 * 基于 Forge Energy 体系的资源类型基类。
 * <p>
 * Forge 1.21.1: Simplified to use NBT-based storage
 * </p>
 */
public class AbstractEnergyStorage extends SimpleResourceType {

    public AbstractEnergyStorage(ResourceLocation id, Component displayName, String nbtKey, int barColor) {
        super(id, displayName, nbtKey, barColor);
    }

    /**
     * Forge 1.21.1 uses a different capability system than NeoForge.
     */
    @Override
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event, Item shield,
                                        int capacity, int maxReceive) {
        // Forge uses AttachCapabilitiesEvent for item capability registration
        // This would need to be handled differently from NeoForge
    }
}
