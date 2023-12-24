package com.oblivioussp.spartanshields.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.oblivioussp.spartanshields.util.Constants;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ObsidianShieldItem extends BasicShieldItem 
{
	public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
	}
	
	public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop) 
	{
		this(toolMaterial, defaultMaxDamage, false, prop);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
		Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.<Attribute, AttributeModifier>create();
		
		if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)
        {
			modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Constants.SHIELD_MOVE_SPEED_UUID, "Shield modifier", -0.3, Operation.MULTIPLY_TOTAL));
			modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(Constants.SHIELD_KNOCKBACK_UUID, "Shield modifier", 0.5, Operation.ADDITION));
        }
		
		return modifiers;
    }
}
