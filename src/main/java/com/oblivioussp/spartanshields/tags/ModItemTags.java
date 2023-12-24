package com.oblivioussp.spartanshields.tags;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.util.Constants;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags 
{
	public static final TagKey<Item> TIN_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/tin"));
	public static final TagKey<Item> BRONZE_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/bronze"));
	public static final TagKey<Item> STEEL_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/steel"));
	public static final TagKey<Item> SILVER_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/silver"));
	public static final TagKey<Item> ELECTRUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/electrum"));
	public static final TagKey<Item> LEAD_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/lead"));
	public static final TagKey<Item> NICKEL_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/nickel"));
	public static final TagKey<Item> INVAR_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/invar"));
	public static final TagKey<Item> CONSTANTAN_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/constantan"));
	public static final TagKey<Item> PLATINUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/platinum"));
	public static final TagKey<Item> ALUMINUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/aluminum"));
	
	public static final TagKey<Item> MANASTEEL_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/manasteel"));
	public static final TagKey<Item> TERRASTEEL_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/terrasteel"));
	public static final TagKey<Item> ELEMENTIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/elementium"));
	public static final TagKey<Item> OSMIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/osmium"));
	public static final TagKey<Item> REFINED_GLOWSTONE_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/refined_glowstone"));
	public static final TagKey<Item> REFINED_OBSIDIAN_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/refined_obsidian"));
	public static final TagKey<Item> SIGNALUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/signalum"));
	public static final TagKey<Item> LUMIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/lumium"));
	public static final TagKey<Item> ENDERIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/enderium"));
	
	public static final TagKey<Item> BASIC_SHIELDS = ItemTags.create(new ResourceLocation(ModSpartanShields.ID, "basic_shields"));
	public static final TagKey<Item> TOWER_SHIELDS = ItemTags.create(new ResourceLocation(ModSpartanShields.ID, "tower_shields"));
	public static final TagKey<Item> SHIELDS_WITH_BASH = ItemTags.create(new ResourceLocation(ModSpartanShields.ID, "shields_with_bash"));
	
	public static final TagKey<Item> MANA_USING_ITEMS = ItemTags.create(new ResourceLocation(Constants.Botania_ModID, "mana_using_items"));
}
