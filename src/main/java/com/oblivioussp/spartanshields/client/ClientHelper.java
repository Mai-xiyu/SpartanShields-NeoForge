package com.oblivioussp.spartanshields.client;

import com.oblivioussp.spartanshields.client.model.ElementiumTowerShieldModel;
import com.oblivioussp.spartanshields.client.model.EnderiumShieldModel;
import com.oblivioussp.spartanshields.client.model.KiteShieldModel;
import com.oblivioussp.spartanshields.client.model.LumiumShieldModel;
import com.oblivioussp.spartanshields.client.model.ManasteelTowerShieldModel;
import com.oblivioussp.spartanshields.client.model.MekanismTowerShieldModel;
import com.oblivioussp.spartanshields.client.model.ShieldBaseModel;
import com.oblivioussp.spartanshields.client.model.TerrasteelTowerShieldModel;
import com.oblivioussp.spartanshields.client.model.TowerShieldModel;
import com.oblivioussp.spartanshields.client.render.item.ModelLayers;
import com.oblivioussp.spartanshields.client.render.item.TowerShieldBEWLR;
import com.oblivioussp.spartanshields.init.ModItems;
import com.oblivioussp.spartanshields.item.FEPoweredShieldItem;
import com.oblivioussp.spartanshields.item.ShieldBaseItem;
import com.oblivioussp.spartanshields.util.Log;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHelper 
{
	public static void registerItemColors()
	{
		Minecraft.getInstance().getItemColors().register(new ItemColor() {
			@Override
			public int getColor(ItemStack stack, int layer) 
			{
				return layer == 1 ? 0x78F083 : 0xFFFFFF;
			}
		}, ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get());
		Minecraft.getInstance().getItemColors().register(new ItemColor() {
			@Override
			public int getColor(ItemStack stack, int layer) 
			{
				return layer == 1 ? 0xF07883 : 0xFFFFFF;
			}
		}, ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get());
		Minecraft.getInstance().getItemColors().register(new ItemColor() {
			@Override
			public int getColor(ItemStack stack, int layer) 
			{
				return layer == 1 ? 0x7883F0 : 0xFFFFFF;
			}
		}, ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get());
		Minecraft.getInstance().getItemColors().register(new ItemColor() {
			@Override
			public int getColor(ItemStack stack, int layer) 
			{
				return layer == 1 ? 0xF083F0 : 0xFFFFFF;
			}
		}, ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get());
	}
	
	public static void registerShieldPropertyOverrides(ShieldBaseItem item)
	{
		ItemProperties.register(item, new ResourceLocation("blocking"), (stack, world, living, value) ->
		{
	         return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
	}
	
	public static void registerPoweredShieldPropertyOverrides(FEPoweredShieldItem item)
	{
		registerShieldPropertyOverrides(item);
		ItemProperties.register(item, new ResourceLocation("disabled"), (stack, world, living, value) -> 
		{
			boolean disabled = stack.getOrCreateTag().getInt(FEPoweredShieldItem.NBT_ENERGY) <= 0 ;
			return disabled ? 1.0f : 0.0f;
		});
	}
	
	/*@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers ev)
	{
	}*/
	
	@SubscribeEvent
	public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions ev)
	{
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
		Log.info("Model Layer registration complete!");
	}
	
	@SubscribeEvent
	public static void reloadClient(RegisterClientReloadListenersEvent ev)
	{
		ev.registerReloadListener(TowerShieldBEWLR.INSTANCE);
	}
}
