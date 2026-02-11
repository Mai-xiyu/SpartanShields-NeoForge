package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

/**
 * Handles capability registration for Forge 1.21+
 * TODO: Adapt for Forge capability system (different from NeoForge)
 */
@Mod.EventBusSubscriber(modid = ModSpartanShields.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityEventHandler {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Forge 1.21.1 uses a different capability registration system than NeoForge
        // The energy capability registration is handled through AttachCapabilitiesEvent instead
        // For now, powered shields may need to be registered differently
    }
}
