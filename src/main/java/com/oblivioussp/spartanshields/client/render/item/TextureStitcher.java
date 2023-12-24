package com.oblivioussp.spartanshields.client.render.item;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.util.Log;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@SuppressWarnings("deprecation")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TextureStitcher 
{
	public static final TowerShieldRenderInfo RENDER_INFO_WOODEN_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/wooden_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/wooden_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_STONE_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/stone_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/stone_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_COPPER_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/copper_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/copper_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_IRON_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/iron_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/iron_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_GOLDEN_TOWER_SHIELD_ = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/golden_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/golden_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_DIAMOND_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/diamond_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/diamond_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_NETHERITE_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/netherite_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/netherite_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_OBSIDIAN_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/obsidian_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/obsidian_tower_shield_pattern"));
	
	public static final TowerShieldRenderInfo RENDER_INFO_TIN_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/tin_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/tin_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_BRONZE_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/bronze_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/bronze_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_STEEL_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/steel_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/steel_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_SILVER_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/silver_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/silver_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_ELECTRUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/electrum_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/electrum_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_LEAD_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/lead_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/lead_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_NICKEL_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/nickel_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/nickel_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_INVAR_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/invar_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/invar_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_CONSTANTAN_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/constantan_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/constantan_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_PLATINUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/platinum_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/platinum_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_ALUMINUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/aluminum_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/aluminum_tower_shield_pattern"));
	
	public static final TowerShieldRenderInfo RENDER_INFO_SIGNALUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/signalum_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/signalum_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_LUMIUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/lumium_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/lumium_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_ENDERIUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/enderium_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/enderium_tower_shield_pattern"));

	public static final TowerShieldRenderInfo RENDER_INFO_MANASTEEL_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/botania/manasteel_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/botania/manasteel_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_TERRASTEEL_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/botania/terrasteel_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/botania/terrasteel_tower_shield_pattern"));
	public static final TowerShieldRenderInfo RENDER_INFO_ELEMENTIUM_TOWER_SHIELD = new TowerShieldRenderInfo(new ResourceLocation(ModSpartanShields.ID, "entity/botania/elementium_tower_shield_nopattern"),
															new ResourceLocation(ModSpartanShields.ID, "entity/botania/elementium_tower_shield_pattern"));
	
	public static final TowerShieldRenderInfo RENDER_INFO_BASIC_MEKANISTS_TOWER_SHIELD = new MekanismTowerShieldRenderInfo(0.46875f, 0.9375f, 0.51171875f);
	public static final TowerShieldRenderInfo RENDER_INFO_ADVANCED_MEKANISTS_TOWER_SHIELD = new MekanismTowerShieldRenderInfo(0.9375f, 0.46875f, 0.51171875f);
	public static final TowerShieldRenderInfo RENDER_INFO_ELITE_MEKANISTS_TOWER_SHIELD = new MekanismTowerShieldRenderInfo(0.46875f, 0.51171875f, 0.9375f);
	public static final TowerShieldRenderInfo RENDER_INFO_ULTIMATE_MEKANISTS_TOWER_SHIELD = new MekanismTowerShieldRenderInfo(0.9375f, 0.51171875f, 0.9375f);
	
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre ev)
	{
		if(ev.getAtlas().location() == TextureAtlas.LOCATION_BLOCKS)
		{
			Log.info("Adding Tower Shield textures to Block Texture Atlas!");
			RENDER_INFO_WOODEN_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_STONE_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_COPPER_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_IRON_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_GOLDEN_TOWER_SHIELD_.stitchTextures(ev);
			RENDER_INFO_DIAMOND_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_NETHERITE_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_OBSIDIAN_TOWER_SHIELD.stitchTextures(ev);
			
			RENDER_INFO_TIN_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_BRONZE_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_STEEL_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_SILVER_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_ELECTRUM_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_LEAD_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_NICKEL_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_INVAR_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_CONSTANTAN_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_PLATINUM_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_ALUMINUM_TOWER_SHIELD.stitchTextures(ev);
			
			RENDER_INFO_SIGNALUM_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_LUMIUM_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_ENDERIUM_TOWER_SHIELD.stitchTextures(ev);
			
			RENDER_INFO_MANASTEEL_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_TERRASTEEL_TOWER_SHIELD.stitchTextures(ev);
			RENDER_INFO_ELEMENTIUM_TOWER_SHIELD.stitchTextures(ev);

			// Same textures for all the Mekanist's tower shields
			RENDER_INFO_BASIC_MEKANISTS_TOWER_SHIELD.stitchTextures(ev);
//			Log.info("Finished adding textures!");
		}
	}
}
