package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.item.FEPoweredShieldItem;
import org.xiyu.spartanshieldsunofficial.util.BuilderRegistry;
import org.xiyu.spartanshieldsunofficial.util.EnergyCapabilityProviderItem;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Handles capability registration for NeoForge 1.21+
 * Registers capabilities for both legacy FEPoweredShieldItem and new Builder-created shields.
 */
@EventBusSubscriber(modid = ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD)
public class CapabilityEventHandler {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // 1. 向后兼容：遍历 ModItems.ITEMS 中旧式的 FEPoweredShieldItem
        for (DeferredHolder<?, ?> holder : org.xiyu.spartanshieldsunofficial.init.ModItems.REGISTER.getEntries()) {
            if (holder.get() instanceof FEPoweredShieldItem poweredShield) {
                event.registerItem(
                        Capabilities.EnergyStorage.ITEM,
                        (stack, context) -> new EnergyCapabilityProviderItem(stack, poweredShield),
                        poweredShield
                );
            }
        }

        // 2. 新 API：遍历 BuilderRegistry 中通过 ShieldBuilder.poweredBy() 创建的盾牌
        //    Item 在被 DeferredRegister 创建时已自注册到 BuilderRegistry，这里直接使用实例
        for (BuilderRegistry.PoweredShieldEntry entry : BuilderRegistry.getRegisteredShields()) {
            Item shield = entry.shield();
            entry.resourceType().onRegisterCapabilities(event, shield, entry.capacity(), entry.maxReceive());
        }
    }
}
