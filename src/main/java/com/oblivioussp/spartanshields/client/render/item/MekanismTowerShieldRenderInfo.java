package com.oblivioussp.spartanshields.client.render.item;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.item.FEPoweredShieldItem;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MekanismTowerShieldRenderInfo extends TowerShieldRenderInfo 
{
	protected final float r, g, b;

	// TODO: Custom shader using the same shader code as the eye shader? (to prevent any potential screwery with shader mods)
	protected static final RenderType LIGHTS_ON = RenderType.eyes(new ResourceLocation(ModSpartanShields.ID, "textures/entity/mekanism/mekanists_tower_shield_lights_on.png"));
	protected static final RenderType LIGHTS_OFF = RenderType.entityTranslucent(new ResourceLocation(ModSpartanShields.ID, "textures/entity/mekanism/mekanists_tower_shield_lights_off.png"));
	

	public MekanismTowerShieldRenderInfo(float rIn, float gIn, float bIn) 
	{
		super(new ResourceLocation(ModSpartanShields.ID, "entity/mekanism/mekanists_tower_shield_nopattern"),
				new ResourceLocation(ModSpartanShields.ID, "entity/mekanism/mekanists_tower_shield_pattern"));
		r = rIn;
		g = gIn;
		b = bIn;
	}
	
	@Override
	public boolean hasLayers() 
	{
		return true;
	}
	
	@Override
	public RenderType getLayerRenderType(ItemStack stack) 
	{
		boolean isPowered = stack.getOrCreateTag().getInt(FEPoweredShieldItem.NBT_ENERGY) != 0;
		return isPowered ? LIGHTS_ON : LIGHTS_OFF;
	}
	
	@Override
	public float getColourRed() 
	{
		return r;
	}
	
	@Override
	public float getColourGreen() 
	{
		return g;
	}

	@Override
	public float getColourBlue() 
	{
		return b;
	}
}
