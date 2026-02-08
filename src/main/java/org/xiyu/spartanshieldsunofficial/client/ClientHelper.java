package org.xiyu.spartanshieldsunofficial.client;

import org.xiyu.spartanshieldsunofficial.client.model.DarkSteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ElementiumTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.EnderiumShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.KiteShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.LumiumShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ManasteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.MekanismTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ShieldBaseModel;
import org.xiyu.spartanshieldsunofficial.client.model.TerrasteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.TowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.render.item.ModelLayers;
import org.xiyu.spartanshieldsunofficial.client.render.item.TowerShieldBEWLR;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.item.FEPoweredShieldItem;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.util.Log;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHelper {
    public static void registerShieldPropertyOverrides(ShieldBaseItem item) {
        ItemProperties.register(item, ResourceLocation.parse("blocking"), (stack, world, living, value) ->
                living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f);
    }

    public static void registerPoweredShieldPropertyOverrides(ShieldBaseItem item) {
        registerShieldPropertyOverrides(item);
        ItemProperties.register(item, ResourceLocation.parse("disabled"), (stack, world, living, value) ->
        {
            // 通用检测：先检查 IResourceStorage 接口，再回退到 STORED_ENERGY
            if (stack.getItem() instanceof org.xiyu.spartanshieldsunofficial.api.resource.IResourceStorage storage) {
                return storage.getResourceType().getStored(stack) <= 0 ? 1.0f : 0.0f;
            }
            boolean disabled = stack.getOrDefault(ModDataComponents.STORED_ENERGY.get(), 0) <= 0;
            return disabled ? 1.0f : 0.0f;
        });
    }

    @SubscribeEvent
    public static void registerItemColours(RegisterColorHandlersEvent.Item ev) {
        ev.register((stack, layer) -> layer == 1 ? 0xFF78F083 : 0xFFFFFFFF, ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get());
        ev.register((stack, layer) -> layer == 1 ? 0xFFF07883 : 0xFFFFFFFF, ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get());
        ev.register((stack, layer) -> layer == 1 ? 0xFF7883F0 : 0xFFFFFFFF, ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get());
        ev.register((stack, layer) -> layer == 1 ? 0xFFF083F0 : 0xFFFFFFFF, ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get());
        ev.register((stack, layer) -> layer == 1 ? 0xFF80FFA0 : 0xFFFFFFFF, ModItems.DARK_STEEL_RIOT_BASIC_SHIELD.get());
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions ev) {
        Log.info("Registering Model Layers!");
        ev.registerLayerDefinition(ModelLayers.BASE_SHIELD, ShieldBaseModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.KITE_SHIELD, KiteShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.TOWER_SHIELD, TowerShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.LUMIUM_SHIELD, LumiumShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.ENDERIUM_SHIELD, EnderiumShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.MANASTEEL_SHIELD, ManasteelTowerShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.TERRASTEEL_SHIELD, TerrasteelTowerShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.ELEMENTIUM_SHIELD, ElementiumTowerShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.MEKANISM_SHIELD, MekanismTowerShieldModel::createLayer);
        ev.registerLayerDefinition(ModelLayers.DARK_STEEL_SHIELD, DarkSteelTowerShieldModel::createLayer);
        Log.info("Model Layer registration complete!");
    }

    @SubscribeEvent
    public static void reloadClient(RegisterClientReloadListenersEvent ev) {
        ev.registerReloadListener(TowerShieldBEWLR.INSTANCE);
    }
}
