package com.oblivioussp.spartanshields.data;

import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.crafting.condition.TypeDisabledCondition;
import com.oblivioussp.spartanshields.init.ModItems;
import com.oblivioussp.spartanshields.init.ModRecipes;
import com.oblivioussp.spartanshields.item.crafting.ShieldBannerRecipeBuilder;
import com.oblivioussp.spartanshields.util.Constants;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeProvider extends RecipeProvider 
{

	public ModRecipeProvider(DataGenerator generator) 
	{
		super(generator);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) 
	{
		TagKey<Item> planks = ItemTags.create(new ResourceLocation("minecraft:planks"));
		TagKey<Item> stick = ItemTags.create(new ResourceLocation("forge:rods/wooden"));
		TagKey<Item> cobblestone = ItemTags.create(new ResourceLocation("forge:cobblestone"));
		TagKey<Item> copperIngot = ItemTags.create(new ResourceLocation("forge:ingots/copper"));
		TagKey<Item> ironIngot = ItemTags.create(new ResourceLocation("forge:ingots/iron"));
		TagKey<Item> goldIngot = ItemTags.create(new ResourceLocation("forge:ingots/gold"));
		TagKey<Item> diamond = ItemTags.create(new ResourceLocation("forge:gems/diamond"));
		TagKey<Item> netheriteIngot = ItemTags.create(new ResourceLocation("forge:ingots/netherite"));
		TagKey<Item> obsidian = ItemTags.create(new ResourceLocation("forge:obsidian"));
		
		TagKey<Item> tinIngot = ItemTags.create(new ResourceLocation("forge:ingots/tin"));
		TagKey<Item> bronzeIngot = ItemTags.create(new ResourceLocation("forge:ingots/bronze"));
		TagKey<Item> steelIngot = ItemTags.create(new ResourceLocation("forge:ingots/steel"));
		TagKey<Item> silverIngot = ItemTags.create(new ResourceLocation("forge:ingots/silver"));
		TagKey<Item> electrumIngot = ItemTags.create(new ResourceLocation("forge:ingots/electrum"));
		TagKey<Item> leadIngot = ItemTags.create(new ResourceLocation("forge:ingots/lead"));
		TagKey<Item> nickelIngot = ItemTags.create(new ResourceLocation("forge:ingots/nickel"));
		TagKey<Item> invarIngot = ItemTags.create(new ResourceLocation("forge:ingots/invar"));
		TagKey<Item> constantanIngot = ItemTags.create(new ResourceLocation("forge:ingots/constantan"));
		TagKey<Item> platinumIngot = ItemTags.create(new ResourceLocation("forge:ingots/platinum"));
		TagKey<Item> aluminumIngot = ItemTags.create(new ResourceLocation("forge:ingots/aluminum"));
		
		TagKey<Item> manasteelIngot = ItemTags.create(new ResourceLocation("forge:ingots/manasteel"));
		TagKey<Item> terrasteelIngot = ItemTags.create(new ResourceLocation("forge:ingots/terrasteel"));
		TagKey<Item> elementiumIngot = ItemTags.create(new ResourceLocation("forge:ingots/elementium"));
		
		TagKey<Item> osmiumIngot = ItemTags.create(new ResourceLocation("forge:ingots/osmium"));
		TagKey<Item> lapis = ItemTags.create(new ResourceLocation("forge:gems/lapis"));
		TagKey<Item> refinedGlowstoneIngot = ItemTags.create(new ResourceLocation("forge:ingots/refined_glowstone"));
		TagKey<Item> refinedObsidianIngot = ItemTags.create(new ResourceLocation("forge:ingots/refined_obsidian"));
		
		TagKey<Item> signalumIngot = ItemTags.create(new ResourceLocation("forge:ingots/signalum"));
		TagKey<Item> lumiumIngot = ItemTags.create(new ResourceLocation("forge:ingots/lumium"));
		TagKey<Item> enderiumIngot = ItemTags.create(new ResourceLocation("forge:ingots/enderium"));
		
		ShapedRecipeBuilder.shaped(ModItems.WOODEN_BASIC_SHIELD.get()).define('#', planks).define('/', stick).pattern(" # ").pattern("#/#").pattern(" # ").group(ModSpartanShields.ID + ":wood_shields").unlockedBy("has_planks", hasItem(planks)).save(consumer);
		ShapedRecipeBuilder.shaped(ModItems.WOODEN_TOWER_SHIELD.get()).define('#', planks).define('/', stick).pattern("###").pattern("#/#").pattern(" # ").group(ModSpartanShields.ID + ":wood_shields").unlockedBy("has_planks", hasItem(planks)).save(consumer);

		basicUpgradeRecipe(ModItems.STONE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), cobblestone, "stone_shields", "has_cobblestone", consumer);
		towerUpgradeRecipe(ModItems.STONE_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), cobblestone, "stone_shields", "has_cobblestone", consumer);
		basicUpgradeRecipe(ModItems.COPPER_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), copperIngot, "copper_shields", "has_copper_ingot", consumer);
		towerUpgradeRecipe(ModItems.COPPER_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), copperIngot, "copper_shields", "has_copper_ingot", consumer);
		basicUpgradeRecipe(ModItems.IRON_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), ironIngot, "iron_shields", "has_iron_ingot", consumer);
		towerUpgradeRecipe(ModItems.IRON_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), ironIngot, "iron_shields", "has_iron_ingot", consumer);
		basicUpgradeRecipe(ModItems.GOLDEN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), goldIngot, "gold_shields", "has_gold_ingot", consumer);
		towerUpgradeRecipe(ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), goldIngot, "gold_shields", "has_gold_ingot", consumer);
		basicUpgradeRecipe(ModItems.DIAMOND_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), diamond, "diamond_shields", "has_diamond", consumer);
		towerUpgradeRecipe(ModItems.DIAMOND_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), diamond, "diamond_shields", "has_diamond", consumer);
		smithingRecipe(ModItems.NETHERITE_BASIC_SHIELD.get(), ModItems.DIAMOND_BASIC_SHIELD.get(), netheriteIngot, "has_netherite_ingot", consumer);
		smithingRecipe(ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(), netheriteIngot, "has_netherite_ingot", consumer);
		conditionalUpgradeRecipe(ModItems.OBSIDIAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), obsidian, "obsidian_shields", "has_obsidian", TypeDisabledCondition.OBSIDIAN, false, consumer);
		conditionalTowerUpgradeRecipe(ModItems.OBSIDIAN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), obsidian, "obsidian_shields", "has_obsidian", TypeDisabledCondition.OBSIDIAN, false, consumer);
		
		conditionalUpgradeRecipe(ModItems.TIN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), tinIngot, "tin_shields", "has_tin_ingot", TypeDisabledCondition.TIN, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.TIN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), tinIngot, "tin_shields", "has_tin_ingot", TypeDisabledCondition.TIN, true, consumer);
		conditionalUpgradeRecipe(ModItems.BRONZE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), bronzeIngot, "bronze_shields", "has_bronze_ingot", TypeDisabledCondition.BRONZE, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), bronzeIngot, "bronze_shields", "has_bronze_ingot", TypeDisabledCondition.BRONZE, true, consumer);
		conditionalUpgradeRecipe(ModItems.STEEL_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), steelIngot, "steel_shields", "has_steel_ingot", TypeDisabledCondition.STEEL, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.STEEL_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), steelIngot, "steel_shields", "has_steel_ingot", TypeDisabledCondition.STEEL, true, consumer);
		conditionalUpgradeRecipe(ModItems.SILVER_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), silverIngot, "silver_shields", "has_silver_ingot", TypeDisabledCondition.SILVER, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.SILVER_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), silverIngot, "silver_shields", "has_silver_ingot", TypeDisabledCondition.SILVER, true, consumer);
		conditionalUpgradeRecipe(ModItems.ELECTRUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), electrumIngot, "electrum_shields", "has_electrum_ingot", TypeDisabledCondition.ELECTRUM, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), electrumIngot, "electrum_shields", "has_electrum_ingot", TypeDisabledCondition.ELECTRUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.LEAD_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), leadIngot, "lead_shields", "has_lead_ingot", TypeDisabledCondition.LEAD, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.LEAD_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), leadIngot, "lead_shields", "has_lead_ingot", TypeDisabledCondition.LEAD, true, consumer);
		conditionalUpgradeRecipe(ModItems.NICKEL_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), nickelIngot, "nickel_shields", "has_nickel_ingot", TypeDisabledCondition.NICKEL, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), nickelIngot, "nickel_shields", "has_nickel_ingot", TypeDisabledCondition.NICKEL, true, consumer);
		conditionalUpgradeRecipe(ModItems.INVAR_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), invarIngot, "invar_shields", "has_invar_ingot", TypeDisabledCondition.INVAR, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.INVAR_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), invarIngot, "invar_shields", "has_invar_ingot", TypeDisabledCondition.INVAR, true, consumer);
		conditionalUpgradeRecipe(ModItems.CONSTANTAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), constantanIngot, "constantan_shields", "has_constantan_ingot", TypeDisabledCondition.CONSTANTAN, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), constantanIngot, "constantan_shields", "has_constantan_ingot", TypeDisabledCondition.CONSTANTAN, true, consumer);
		conditionalUpgradeRecipe(ModItems.PLATINUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), platinumIngot, "platinum_shields", "has_platinum_ingot", TypeDisabledCondition.PLATINUM, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.PLATINUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), platinumIngot, "platinum_shields", "has_platinum_ingot", TypeDisabledCondition.PLATINUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.ALUMINUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), aluminumIngot, "aluminum_shields", "has_aluminum_ingot", TypeDisabledCondition.ALUMINUM, true, consumer);
		conditionalTowerUpgradeRecipe(ModItems.ALUMINUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), aluminumIngot, "aluminum_shields", "has_aluminum_ingot", TypeDisabledCondition.ALUMINUM, true, consumer);
		
		if(ModList.get().isLoaded(Constants.Botania_ModID))
		{
			Item livingwoodTwig = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:livingwood_twig"));
			Item runeMana = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_mana"));
			ConditionalShapedRecipeBuilder.shaped(ModItems.MANASTEEL_BASIC_SHIELD.get()).define('#', manasteelIngot).define('/', livingwoodTwig).
				define('e', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_earth"))).define('m', runeMana).
				pattern("#e#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_manasteel_ingot", hasItem(manasteelIngot)).save(consumer);
			ConditionalShapedRecipeBuilder.shaped(ModItems.TERRASTEEL_BASIC_SHIELD.get()).define('#', terrasteelIngot).define('/', livingwoodTwig).
				define('p', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_pride"))).define('m', runeMana).
				pattern(" p ").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_terrasteel_ingot", hasItem(terrasteelIngot)).save(consumer);
			ConditionalShapedRecipeBuilder.shaped(ModItems.ELEMENTIUM_BASIC_SHIELD.get()).define('#', elementiumIngot).define('/', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:dreamwood_twig"))).
				define('s', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_summer"))).define('m', runeMana).
				pattern("#s#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_elementium_ingot", hasItem(elementiumIngot)).save(consumer);
			
			ConditionalShapedRecipeBuilder.shaped(ModItems.MANASTEEL_TOWER_SHIELD.get()).define('#', manasteelIngot).define('/', livingwoodTwig).
				define('e', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_earth"))).define('m', runeMana).
				pattern("#e#").pattern("#/#").pattern("#m#").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_manasteel_ingot", hasItem(manasteelIngot)).save(consumer);
			ConditionalShapedRecipeBuilder.shaped(ModItems.TERRASTEEL_TOWER_SHIELD.get()).define('#', terrasteelIngot).define('/', livingwoodTwig).
				define('p', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_pride"))).define('m', runeMana).
				pattern("#p#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_terrasteel_ingot", hasItem(terrasteelIngot)).save(consumer);
			ConditionalShapedRecipeBuilder.shaped(ModItems.ELEMENTIUM_TOWER_SHIELD.get()).define('#', elementiumIngot).define('/', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:dreamwood_twig"))).
				define('s', ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:rune_summer"))).define('m', runeMana).
				pattern("#s#").pattern("#/#").pattern("#m#").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_elementium_ingot", hasItem(elementiumIngot)).save(consumer);
		}
		
		conditionalModUpgradeRecipe(ModItems.OSMIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), osmiumIngot, "osmium_shields", "has_osmium_ingot", Constants.Mekanism_ModID, consumer);
		conditionalModUpgradeRecipe(ModItems.LAPIS_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), lapis, "lapis_shields", "has_lapis_lazuli", Constants.Mekanism_ModID, consumer);
		conditionalModUpgradeRecipe(ModItems.REFINED_GLOWSTONE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), refinedGlowstoneIngot, "refined_glowstone_shields", "has_refined_glowstone_ingot", Constants.Mekanism_ModID, consumer);
		conditionalModUpgradeRecipe(ModItems.REFINED_OBSIDIAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), refinedObsidianIngot, "refined_obsidian_shields", "has_refined_obsidian_ingot", Constants.Mekanism_ModID, consumer);
	
		if(ModList.get().isLoaded(Constants.Mekanism_ModID))
		{
			Item energyTablet = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:energy_tablet"));
			Item infusedAlloy = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:alloy_infused"));
			Item reinforcedAlloy = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:alloy_reinforced"));
			Item atomicAlloy = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:alloy_atomic"));
			Item enrichedDiamond = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:enriched_diamond"));
			Item basicControlCircuit = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:basic_control_circuit"));
			Item advancedControlCircuit = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:advanced_control_circuit"));
			Item eliteControlCircuit = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:elite_control_circuit"));
			Item ultimateControlCircuit = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:ultimate_control_circuit"));
			
			mekanismShieldRecipe(ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), infusedAlloy, enrichedDiamond, basicControlCircuit, energyTablet, steelIngot, consumer);
			mekanismShieldUpgradeRecipe(ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), reinforcedAlloy, advancedControlCircuit, energyTablet, steelIngot, "has_basic_mekanists_shield", consumer);
			mekanismShieldUpgradeRecipe(ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), atomicAlloy, eliteControlCircuit, energyTablet, steelIngot, "has_advanced_mekanists_shield", consumer);
			mekanismShieldUpgradeRecipe(ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), atomicAlloy, ultimateControlCircuit, energyTablet, steelIngot, "has_elite_mekanists_shield", consumer);
/*			ConditionalShapedRecipeBuilder.shaped(ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get()).
				define('a', infusedAlloy).define('d', enrichedDiamond).define('c', basicControlCircuit).define('b', energyTablet).
				define('#', steelIngot).define('O', ModItems.WOODEN_BASIC_SHIELD.get()).
				pattern("ada").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_energy_tablet", hasItem(energyTablet)).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get()).
				define('a', reinforcedAlloy).define('c', advancedControlCircuit).
				define('O', ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_basic_mekanists_shield", hasItem(ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get())).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get()).
				define('a', atomicAlloy).define('c', eliteControlCircuit).
				define('O', ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_advanced_mekanists_shield", hasItem(ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get())).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get()).
				define('a', atomicAlloy).define('c', ultimateControlCircuit).
				define('O', ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_elite_mekanists_shield", hasItem(ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get())).
				save(consumer);*/
			
			mekanismShieldRecipe(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), infusedAlloy, enrichedDiamond, basicControlCircuit, energyTablet, steelIngot, consumer);			
			mekanismShieldUpgradeRecipe(ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), reinforcedAlloy, advancedControlCircuit, energyTablet, steelIngot, "has_basic_mekanists_shield", consumer);
			mekanismShieldUpgradeRecipe(ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), atomicAlloy, eliteControlCircuit, energyTablet, steelIngot, "has_advanced_mekanists_shield", consumer);
			mekanismShieldUpgradeRecipe(ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), atomicAlloy, ultimateControlCircuit, energyTablet, steelIngot, "has_elite_mekanists_shield", consumer);
/*			ConditionalShapedRecipeBuilder.shaped(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get()).
				define('a', infusedAlloy).define('d', enrichedDiamond).define('c', basicControlCircuit).
				define('b', energyTablet).define('#', steelIngot).define('O', ModItems.WOODEN_TOWER_SHIELD.get()).
				pattern("ada").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_energy_tablet", hasItem(energyTablet)).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get()).
				define('a', reinforcedAlloy).define('c', advancedControlCircuit).
				define('O', ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_basic_mekanists_shield", hasItem(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get())).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get()).
				define('a', atomicAlloy).define('c', eliteControlCircuit).
				define('O', ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_advanced_mekanists_shield", hasItem(ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get())).
				save(consumer);
			ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get()).
				define('a', atomicAlloy).define('c', ultimateControlCircuit).
				define('O', ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get()).define('b', energyTablet).define('#', steelIngot).
				pattern("a a").pattern("bOb").pattern("#c#").
				condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
				unlockedBy("has_elite_mekanists_shield", hasItem(ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get())).
				save(consumer);*/
			
		}

		conditionalUpgradeRecipe(ModItems.SIGNALUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), signalumIngot, "signalum_shields", "has_signalum_ingot", TypeDisabledCondition.SIGNALUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), signalumIngot, "signalum_shields", "has_signalum_ingot", TypeDisabledCondition.SIGNALUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.LUMIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), lumiumIngot, "lumium_shields", "has_lumium_ingot", TypeDisabledCondition.LUMIUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), lumiumIngot, "lumium_shields", "has_lumium_ingot", TypeDisabledCondition.LUMIUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.ENDERIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), enderiumIngot, "enderium_shields", "has_enderium_ingot", TypeDisabledCondition.ENDERIUM, true, consumer);
		conditionalUpgradeRecipe(ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), enderiumIngot, "enderium_shields", "has_enderium_ingot", TypeDisabledCondition.ENDERIUM, true, consumer);
		
		ImmutableList.of(ModItems.WOODEN_TOWER_SHIELD.get(), ModItems.STONE_TOWER_SHIELD.get(), ModItems.COPPER_TOWER_SHIELD.get(), ModItems.IRON_TOWER_SHIELD.get(), ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(),
			ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.OBSIDIAN_TOWER_SHIELD.get(), ModItems.TIN_TOWER_SHIELD.get(), ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.STEEL_TOWER_SHIELD.get(), ModItems.SILVER_TOWER_SHIELD.get(),
			ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.LEAD_TOWER_SHIELD.get(), ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.INVAR_TOWER_SHIELD.get(), ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.PLATINUM_TOWER_SHIELD.get(),
			ModItems.ALUMINUM_TOWER_SHIELD.get(),ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get(),
			ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get()).
			forEach((shield) -> ShieldBannerRecipeBuilder.recipe(shield).save(consumer));
	}
	
	private void basicUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, Consumer<FinishedRecipe> consumer)
	{
		ShapedRecipeBuilder.shaped(result).define('O', baseShield).define('#', material).pattern(" # ").pattern("#O#").pattern(" # ").group(ModSpartanShields.ID + ":" + group).unlockedBy(unlockName, hasItem(material)).save(consumer);
	}
	
	private void towerUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, Consumer<FinishedRecipe> consumer)
	{
		ShapedRecipeBuilder.shaped(result).define('O', baseShield).define('#', material).pattern("###").pattern("#O#").pattern(" # ").group(ModSpartanShields.ID + ":" + group).unlockedBy(unlockName, hasItem(material)).save(consumer);
	}
	
	private void smithingRecipe(ItemLike result, ItemLike base, TagKey<Item> material, String unlockName, Consumer<FinishedRecipe> consumer)
	{
		UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(material), result.asItem()).unlocks(unlockName, hasItem(material)).save(consumer, ForgeRegistries.ITEMS.getKey(result.asItem()) + "_smithing");
	}
	
	private void conditionalUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String disabledName, boolean isModded, Consumer<FinishedRecipe> consumer)
	{
		ImmutableList<String> disableList = buildDisableList(disabledName, isModded);
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).
			define('#', material).pattern(" # ").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(disableList)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(consumer);
	}
	
	private void conditionalTowerUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String disabledName, boolean isModded, Consumer<FinishedRecipe> consumer)
	{
		ImmutableList<String> disableList = buildDisableList(disabledName, isModded);
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).define('#', material).
			pattern("###").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(disableList)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(consumer);
	}
	
	private void conditionalModUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String modName, Consumer<FinishedRecipe> consumer)
	{
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).define('#', material).
			pattern(" # ").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED))).
			condition(new ModLoadedCondition(modName)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(consumer);
	}
	
	private void mekanismShieldRecipe(ItemLike result, ItemLike baseShield, Item infusedAlloy, Item enrichedDiamond, Item basicControlCircuit, Item energyTablet, TagKey<Item> steelIngot, Consumer<FinishedRecipe> consumer)
	{
		ConditionalShapedRecipeBuilder.shaped(result).
			define('a', infusedAlloy).define('d', enrichedDiamond).define('c', basicControlCircuit).define('b', energyTablet).
			define('#', steelIngot).define('O', baseShield).
			pattern("ada").pattern("bOb").pattern("#c#").
			condition(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED))).
			condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
			unlockedBy("has_energy_tablet", hasItem(energyTablet)).
			save(consumer);
	}
	
	private void mekanismShieldUpgradeRecipe(ItemLike result, ItemLike baseShield, Item alloy, Item controlCircuit, Item energyTablet, TagKey<Item> steelIngot, String unlockName, Consumer<FinishedRecipe> consumer)
	{
		ConditionalShapedRecipeBuilder.shapedCustom(ModRecipes.POWERED_SHIELD_UPGRADE.get(), result).
			define('a', alloy).define('c', controlCircuit).
			define('O', baseShield).define('b', energyTablet).define('#', steelIngot).
			pattern("a a").pattern("bOb").pattern("#c#").
			condition(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED))).
			condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
			unlockedBy(unlockName, hasItem(baseShield)).
			save(consumer);
	}
	
	private ImmutableList<String> buildDisableList(String disabledName, boolean isModded)
	{
		Builder<String> listBuilder = ImmutableList.<String>builder();
		if(isModded)
			listBuilder.add(TypeDisabledCondition.MODDED);
		listBuilder.add(disabledName);
		return listBuilder.build();
	}
	
	private InventoryChangeTrigger.TriggerInstance hasItem(TagKey<Item> tag)
	{
		return makeInventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
	}
	
	private InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item)
	{
		return makeInventoryTrigger(ItemPredicate.Builder.item().of(item).build());
	}
	
	private InventoryChangeTrigger.TriggerInstance makeInventoryTrigger(ItemPredicate... predicates)
	{
		return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicates);		
	}
	
	@Override
	public String getName()
	{
		return ModSpartanShields.NAME + " Recipes";
	}
}
