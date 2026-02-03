package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.item.FEPoweredShieldItem;
import org.xiyu.spartanshieldsunofficial.item.IItemPoweredFE;
import org.xiyu.spartanshieldsunofficial.util.EnergyCapabilityProviderItem;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Handles capability registration for NeoForge 1.21+
 * In 1.21+, capabilities are registered via RegisterCapabilitiesEvent instead of ICapabilityProvider
 */
@EventBusSubscriber(modid = ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD)
public class CapabilityEventHandler
{
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        // Register energy capability for all items that implement IItemPoweredFE
        for (DeferredHolder<?, ?> holder : org.xiyu.spartanshieldsunofficial.init.ModItems.REGISTER.getEntries())
        {
            if (holder.get() instanceof FEPoweredShieldItem poweredShield)
            {
                event.registerItem(
                    Capabilities.EnergyStorage.ITEM,
                    (stack, context) -> new EnergyCapabilityProviderItem(stack, poweredShield),
                    poweredShield
                );
            }
        }
    }
}
