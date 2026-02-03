package org.xiyu.spartanshieldsunofficial.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.util.Constants;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ObsidianShieldItem extends BasicShieldItem 
{
	// Resource locations for attribute modifiers
	private static final ResourceLocation MOVE_SPEED_MODIFIER = ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, "obsidian_shield_move_speed");
	private static final ResourceLocation KNOCKBACK_MODIFIER = ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, "obsidian_shield_knockback");
	
	public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
	}
	
	public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop) 
	{
		this(toolMaterial, defaultMaxDamage, false, prop);
	}
}
