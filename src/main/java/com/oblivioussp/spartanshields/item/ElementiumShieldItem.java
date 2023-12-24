package com.oblivioussp.spartanshields.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.handler.PixieHandler;

public class ElementiumShieldItem extends BotaniaShieldItem
{

	public ElementiumShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, int manaPerDamage,
			Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, manaPerDamage, prop);
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.<Attribute, AttributeModifier>create();
		if(slot == EquipmentSlot.OFFHAND || slot == EquipmentSlot.MAINHAND)
			modifiers.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "Shield modifier", 0.1));
		return modifiers;
	}

}
