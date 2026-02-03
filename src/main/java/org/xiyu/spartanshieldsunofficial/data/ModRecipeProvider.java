package org.xiyu.spartanshieldsunofficial.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.crafting.condition.TypeDisabledCondition;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.init.ModRecipes;
import org.xiyu.spartanshieldsunofficial.item.crafting.ShieldBannerRecipeBuilder;
import org.xiyu.spartanshieldsunofficial.util.Constants;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.ModList;

public class ModRecipeProvider extends RecipeProvider 
{
	private RecipeOutput output;

	public ModRecipeProvider(PackOutput output, java.util.concurrent.CompletableFuture<HolderLookup.Provider> registries)
	{
		super(output, registries);
	}
	
	@Override
	protected void buildRecipes(RecipeOutput output)
	{
		this.output = output;
		TagKey<Item> planks = ItemTags.create(ResourceLocation.parse("minecraft:planks"));
		TagKey<Item> stick = ItemTags.create(ResourceLocation.parse("c:rods/wooden"));
		TagKey<Item> cobblestone = ItemTags.create(ResourceLocation.parse("c:cobblestones"));
		TagKey<Item> copperIngot = ItemTags.create(ResourceLocation.parse("c:ingots/copper"));
		TagKey<Item> ironIngot = ItemTags.create(ResourceLocation.parse("c:ingots/iron"));
		TagKey<Item> goldIngot = ItemTags.create(ResourceLocation.parse("c:ingots/gold"));
		TagKey<Item> diamond = ItemTags.create(ResourceLocation.parse("c:gems/diamond"));
		TagKey<Item> netheriteIngot = ItemTags.create(ResourceLocation.parse("c:ingots/netherite"));
		TagKey<Item> obsidian = ItemTags.create(ResourceLocation.parse("c:obsidians"));
		
		TagKey<Item> tinIngot = ItemTags.create(ResourceLocation.parse("c:ingots/tin"));
		TagKey<Item> bronzeIngot = ItemTags.create(ResourceLocation.parse("c:ingots/bronze"));
		TagKey<Item> steelIngot = ItemTags.create(ResourceLocation.parse("c:ingots/steel"));
		TagKey<Item> silverIngot = ItemTags.create(ResourceLocation.parse("c:ingots/silver"));
		TagKey<Item> electrumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/electrum"));
		TagKey<Item> leadIngot = ItemTags.create(ResourceLocation.parse("c:ingots/lead"));
		TagKey<Item> nickelIngot = ItemTags.create(ResourceLocation.parse("c:ingots/nickel"));
		TagKey<Item> invarIngot = ItemTags.create(ResourceLocation.parse("c:ingots/invar"));
		TagKey<Item> constantanIngot = ItemTags.create(ResourceLocation.parse("c:ingots/constantan"));
		TagKey<Item> platinumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/platinum"));
		TagKey<Item> aluminumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/aluminum"));
		
		TagKey<Item> manasteelIngot = ItemTags.create(ResourceLocation.parse("c:ingots/manasteel"));
		TagKey<Item> terrasteelIngot = ItemTags.create(ResourceLocation.parse("c:ingots/terrasteel"));
		TagKey<Item> elementiumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/elementium"));
		
		TagKey<Item> osmiumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/osmium"));
		TagKey<Item> lapis = ItemTags.create(ResourceLocation.parse("c:gems/lapis"));
		TagKey<Item> refinedGlowstoneIngot = ItemTags.create(ResourceLocation.parse("c:ingots/refined_glowstone"));
		TagKey<Item> refinedObsidianIngot = ItemTags.create(ResourceLocation.parse("c:ingots/refined_obsidian"));
		
		TagKey<Item> signalumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/signalum"));
		TagKey<Item> lumiumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/lumium"));
		TagKey<Item> enderiumIngot = ItemTags.create(ResourceLocation.parse("c:ingots/enderium"));
		
		ConditionalShapedRecipeBuilder.shaped(ModItems.WOODEN_BASIC_SHIELD.get()).define('#', planks).define('/', stick).pattern(" # ").pattern("#/#").pattern(" # ").group(ModSpartanShields.ID + ":wood_shields").unlockedBy("has_planks", hasItem(planks)).save(output);
		ConditionalShapedRecipeBuilder.shaped(ModItems.WOODEN_TOWER_SHIELD.get()).define('#', planks).define('/', stick).pattern("###").pattern("#/#").pattern(" # ").group(ModSpartanShields.ID + ":wood_shields").unlockedBy("has_planks", hasItem(planks)).save(output);

		basicUpgradeRecipe(ModItems.STONE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), cobblestone, "stone_shields", "has_cobblestone");
		towerUpgradeRecipe(ModItems.STONE_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), cobblestone, "stone_shields", "has_cobblestone");
		basicUpgradeRecipe(ModItems.COPPER_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), copperIngot, "copper_shields", "has_copper_ingot");
		towerUpgradeRecipe(ModItems.COPPER_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), copperIngot, "copper_shields", "has_copper_ingot");
		basicUpgradeRecipe(ModItems.IRON_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), ironIngot, "iron_shields", "has_iron_ingot");
		towerUpgradeRecipe(ModItems.IRON_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), ironIngot, "iron_shields", "has_iron_ingot");
		basicUpgradeRecipe(ModItems.GOLDEN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), goldIngot, "gold_shields", "has_gold_ingot");
		towerUpgradeRecipe(ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), goldIngot, "gold_shields", "has_gold_ingot");
		basicUpgradeRecipe(ModItems.DIAMOND_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), diamond, "diamond_shields", "has_diamond");
		towerUpgradeRecipe(ModItems.DIAMOND_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), diamond, "diamond_shields", "has_diamond");
		smithingRecipe(ModItems.NETHERITE_BASIC_SHIELD.get(), ModItems.DIAMOND_BASIC_SHIELD.get(), netheriteIngot, "has_netherite_ingot");
		smithingRecipe(ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(), netheriteIngot, "has_netherite_ingot");
		conditionalUpgradeRecipe(ModItems.OBSIDIAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), obsidian, "obsidian_shields", "has_obsidian", TypeDisabledCondition.OBSIDIAN, false);
		conditionalTowerUpgradeRecipe(ModItems.OBSIDIAN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), obsidian, "obsidian_shields", "has_obsidian", TypeDisabledCondition.OBSIDIAN, false);
		
		conditionalUpgradeRecipe(ModItems.TIN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), tinIngot, "tin_shields", "has_tin_ingot", TypeDisabledCondition.TIN, true);
		conditionalTowerUpgradeRecipe(ModItems.TIN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), tinIngot, "tin_shields", "has_tin_ingot", TypeDisabledCondition.TIN, true);
		conditionalUpgradeRecipe(ModItems.BRONZE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), bronzeIngot, "bronze_shields", "has_bronze_ingot", TypeDisabledCondition.BRONZE, true);
		conditionalTowerUpgradeRecipe(ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), bronzeIngot, "bronze_shields", "has_bronze_ingot", TypeDisabledCondition.BRONZE, true);
		conditionalUpgradeRecipe(ModItems.STEEL_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), steelIngot, "steel_shields", "has_steel_ingot", TypeDisabledCondition.STEEL, true);
		conditionalTowerUpgradeRecipe(ModItems.STEEL_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), steelIngot, "steel_shields", "has_steel_ingot", TypeDisabledCondition.STEEL, true);
		conditionalUpgradeRecipe(ModItems.SILVER_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), silverIngot, "silver_shields", "has_silver_ingot", TypeDisabledCondition.SILVER, true);
		conditionalTowerUpgradeRecipe(ModItems.SILVER_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), silverIngot, "silver_shields", "has_silver_ingot", TypeDisabledCondition.SILVER, true);
		conditionalUpgradeRecipe(ModItems.ELECTRUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), electrumIngot, "electrum_shields", "has_electrum_ingot", TypeDisabledCondition.ELECTRUM, true);
		conditionalTowerUpgradeRecipe(ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), electrumIngot, "electrum_shields", "has_electrum_ingot", TypeDisabledCondition.ELECTRUM, true);
		conditionalUpgradeRecipe(ModItems.LEAD_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), leadIngot, "lead_shields", "has_lead_ingot", TypeDisabledCondition.LEAD, true);
		conditionalTowerUpgradeRecipe(ModItems.LEAD_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), leadIngot, "lead_shields", "has_lead_ingot", TypeDisabledCondition.LEAD, true);
		conditionalUpgradeRecipe(ModItems.NICKEL_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), nickelIngot, "nickel_shields", "has_nickel_ingot", TypeDisabledCondition.NICKEL, true);
		conditionalTowerUpgradeRecipe(ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), nickelIngot, "nickel_shields", "has_nickel_ingot", TypeDisabledCondition.NICKEL, true);
		conditionalUpgradeRecipe(ModItems.INVAR_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), invarIngot, "invar_shields", "has_invar_ingot", TypeDisabledCondition.INVAR, true);
		conditionalTowerUpgradeRecipe(ModItems.INVAR_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), invarIngot, "invar_shields", "has_invar_ingot", TypeDisabledCondition.INVAR, true);
		conditionalUpgradeRecipe(ModItems.CONSTANTAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), constantanIngot, "constantan_shields", "has_constantan_ingot", TypeDisabledCondition.CONSTANTAN, true);
		conditionalTowerUpgradeRecipe(ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), constantanIngot, "constantan_shields", "has_constantan_ingot", TypeDisabledCondition.CONSTANTAN, true);
		conditionalUpgradeRecipe(ModItems.PLATINUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), platinumIngot, "platinum_shields", "has_platinum_ingot", TypeDisabledCondition.PLATINUM, true);
		conditionalTowerUpgradeRecipe(ModItems.PLATINUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), platinumIngot, "platinum_shields", "has_platinum_ingot", TypeDisabledCondition.PLATINUM, true);
		conditionalUpgradeRecipe(ModItems.ALUMINUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), aluminumIngot, "aluminum_shields", "has_aluminum_ingot", TypeDisabledCondition.ALUMINUM, true);
		conditionalTowerUpgradeRecipe(ModItems.ALUMINUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), aluminumIngot, "aluminum_shields", "has_aluminum_ingot", TypeDisabledCondition.ALUMINUM, true);
		
		if(ModList.get().isLoaded(Constants.Botania_ModID))
		{
			Item livingwoodTwig = BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:livingwood_twig"));
			Item runeMana = BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_mana"));
			ConditionalShapedRecipeBuilder.shaped(ModItems.MANASTEEL_BASIC_SHIELD.get()).define('#', manasteelIngot).define('/', livingwoodTwig).
				define('e', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_earth"))).define('m', runeMana).
				pattern("#e#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_manasteel_ingot", hasItem(manasteelIngot)).save(output);
			ConditionalShapedRecipeBuilder.shaped(ModItems.TERRASTEEL_BASIC_SHIELD.get()).define('#', terrasteelIngot).define('/', livingwoodTwig).
				define('p', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_pride"))).define('m', runeMana).
				pattern(" p ").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_terrasteel_ingot", hasItem(terrasteelIngot)).save(output);
			ConditionalShapedRecipeBuilder.shaped(ModItems.ELEMENTIUM_BASIC_SHIELD.get()).define('#', elementiumIngot).define('/', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:dreamwood_twig"))).
				define('s', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_summer"))).define('m', runeMana).
				pattern("#s#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_elementium_ingot", hasItem(elementiumIngot)).save(output);
			
			ConditionalShapedRecipeBuilder.shaped(ModItems.MANASTEEL_TOWER_SHIELD.get()).define('#', manasteelIngot).define('/', livingwoodTwig).
				define('e', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_earth"))).define('m', runeMana).
				pattern("#e#").pattern("#/#").pattern("#m#").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_manasteel_ingot", hasItem(manasteelIngot)).save(output);
			ConditionalShapedRecipeBuilder.shaped(ModItems.TERRASTEEL_TOWER_SHIELD.get()).define('#', terrasteelIngot).define('/', livingwoodTwig).
				define('p', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_pride"))).define('m', runeMana).
				pattern("#p#").pattern("#/#").pattern(" m ").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_terrasteel_ingot", hasItem(terrasteelIngot)).save(output);
			ConditionalShapedRecipeBuilder.shaped(ModItems.ELEMENTIUM_TOWER_SHIELD.get()).define('#', elementiumIngot).define('/', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:dreamwood_twig"))).
				define('s', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botania:rune_summer"))).define('m', runeMana).
				pattern("#s#").pattern("#/#").pattern("#m#").condition(new ModLoadedCondition(Constants.Botania_ModID)).unlockedBy("has_elementium_ingot", hasItem(elementiumIngot)).save(output);
		}
		
		conditionalModUpgradeRecipe(ModItems.OSMIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), osmiumIngot, "osmium_shields", "has_osmium_ingot", Constants.Mekanism_ModID);
		conditionalModUpgradeRecipe(ModItems.LAPIS_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), lapis, "lapis_shields", "has_lapis_lazuli", Constants.Mekanism_ModID);
		conditionalModUpgradeRecipe(ModItems.REFINED_GLOWSTONE_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), refinedGlowstoneIngot, "refined_glowstone_shields", "has_refined_glowstone_ingot", Constants.Mekanism_ModID);
		conditionalModUpgradeRecipe(ModItems.REFINED_OBSIDIAN_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), refinedObsidianIngot, "refined_obsidian_shields", "has_refined_obsidian_ingot", Constants.Mekanism_ModID);
	
		if(ModList.get().isLoaded(Constants.Mekanism_ModID))
		{
			Item energyTablet = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:energy_tablet"));
			Item infusedAlloy = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:alloy_infused"));
			Item reinforcedAlloy = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:alloy_reinforced"));
			Item atomicAlloy = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:alloy_atomic"));
			Item enrichedDiamond = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:enriched_diamond"));
			Item basicControlCircuit = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:basic_control_circuit"));
			Item advancedControlCircuit = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:advanced_control_circuit"));
			Item eliteControlCircuit = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:elite_control_circuit"));
			Item ultimateControlCircuit = BuiltInRegistries.ITEM.get(ResourceLocation.parse("mekanism:ultimate_control_circuit"));
			
			mekanismShieldRecipe(ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), infusedAlloy, enrichedDiamond, basicControlCircuit, energyTablet, steelIngot);
			mekanismShieldUpgradeRecipe(ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), reinforcedAlloy, advancedControlCircuit, energyTablet, steelIngot, "has_basic_mekanists_shield");
			mekanismShieldUpgradeRecipe(ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), atomicAlloy, eliteControlCircuit, energyTablet, steelIngot, "has_advanced_mekanists_shield");
			mekanismShieldUpgradeRecipe(ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), atomicAlloy, ultimateControlCircuit, energyTablet, steelIngot, "has_elite_mekanists_shield");
		
			mekanismShieldRecipe(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), infusedAlloy, enrichedDiamond, basicControlCircuit, energyTablet, steelIngot);			
			mekanismShieldUpgradeRecipe(ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), reinforcedAlloy, advancedControlCircuit, energyTablet, steelIngot, "has_basic_mekanists_shield");
			mekanismShieldUpgradeRecipe(ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), atomicAlloy, eliteControlCircuit, energyTablet, steelIngot, "has_advanced_mekanists_shield");
			mekanismShieldUpgradeRecipe(ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), atomicAlloy, ultimateControlCircuit, energyTablet, steelIngot, "has_elite_mekanists_shield");

		}
		if(ModList.get().isLoaded(Constants.EnderIO_ModID))
		{
			Item octadicCapacitor = BuiltInRegistries.ITEM.get(ResourceLocation.parse("enderio:octadic_capacitor"));
			Item darkSteelIngot = BuiltInRegistries.ITEM.get(ResourceLocation.parse("enderio:dark_steel_ingot"));
			Item pulsatingCrystal = BuiltInRegistries.ITEM.get(ResourceLocation.parse("enderio:pulsating_crystal"));
			Item vibrantCrystal = BuiltInRegistries.ITEM.get(ResourceLocation.parse("enderio:vibrant_crystal"));
			
			ConditionalShapedRecipeBuilder.shaped(ModItems.DARK_STEEL_RIOT_BASIC_SHIELD.get()).define('O', ModItems.WOODEN_BASIC_SHIELD.get()).define('#', darkSteelIngot).
				define('c', octadicCapacitor).define('p', pulsatingCrystal).define('v', vibrantCrystal).
				pattern("#v#").pattern("cOc").pattern("#p#").condition(new ModLoadedCondition(Constants.EnderIO_ModID)).unlockedBy("has_octadic_capacitor", hasItem(octadicCapacitor)).save(output);
			ConditionalShapedRecipeBuilder.shaped(ModItems.DARK_STEEL_RIOT_TOWER_SHIELD.get()).define('O', ModItems.WOODEN_TOWER_SHIELD.get()).define('#', darkSteelIngot).
			define('c', octadicCapacitor).define('p', pulsatingCrystal).define('v', vibrantCrystal).
			pattern("#v#").pattern("cOc").pattern("#p#").condition(new ModLoadedCondition(Constants.EnderIO_ModID)).unlockedBy("has_octadic_capacitor", hasItem(octadicCapacitor)).save(output);
		}

		conditionalUpgradeRecipe(ModItems.SIGNALUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), signalumIngot, "signalum_shields", "has_signalum_ingot", TypeDisabledCondition.SIGNALUM, true);
		conditionalUpgradeRecipe(ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), signalumIngot, "signalum_shields", "has_signalum_ingot", TypeDisabledCondition.SIGNALUM, true);
		conditionalUpgradeRecipe(ModItems.LUMIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), lumiumIngot, "lumium_shields", "has_lumium_ingot", TypeDisabledCondition.LUMIUM, true);
		conditionalUpgradeRecipe(ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), lumiumIngot, "lumium_shields", "has_lumium_ingot", TypeDisabledCondition.LUMIUM, true);
		conditionalUpgradeRecipe(ModItems.ENDERIUM_BASIC_SHIELD.get(), ModItems.WOODEN_BASIC_SHIELD.get(), enderiumIngot, "enderium_shields", "has_enderium_ingot", TypeDisabledCondition.ENDERIUM, true);
		conditionalUpgradeRecipe(ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), enderiumIngot, "enderium_shields", "has_enderium_ingot", TypeDisabledCondition.ENDERIUM, true);
		
		ImmutableList.of(ModItems.WOODEN_TOWER_SHIELD.get(), ModItems.STONE_TOWER_SHIELD.get(), ModItems.COPPER_TOWER_SHIELD.get(), ModItems.IRON_TOWER_SHIELD.get(), ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(),
			ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.OBSIDIAN_TOWER_SHIELD.get(), ModItems.TIN_TOWER_SHIELD.get(), ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.STEEL_TOWER_SHIELD.get(), ModItems.SILVER_TOWER_SHIELD.get(),
			ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.LEAD_TOWER_SHIELD.get(), ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.INVAR_TOWER_SHIELD.get(), ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.PLATINUM_TOWER_SHIELD.get(),
			ModItems.ALUMINUM_TOWER_SHIELD.get(),ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get(),
			ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get()).
			forEach((shield) -> ShieldBannerRecipeBuilder.recipe(shield).save(output));
	}
	
	private void basicUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName)
	{
		ConditionalShapedRecipeBuilder.shaped(result).define('O', baseShield).define('#', material).pattern(" # ").pattern("#O#").pattern(" # ").group(ModSpartanShields.ID + ":" + group).unlockedBy(unlockName, hasItem(material)).save(output);
	}
	
	private void towerUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName)
	{
		ConditionalShapedRecipeBuilder.shaped(result).define('O', baseShield).define('#', material).pattern("###").pattern("#O#").pattern(" # ").group(ModSpartanShields.ID + ":" + group).unlockedBy(unlockName, hasItem(material)).save(output);
	}
	
	private void smithingRecipe(ItemLike result, ItemLike base, TagKey<Item> material, String unlockName)
	{
		ResourceLocation id = ResourceLocation.parse(BuiltInRegistries.ITEM.getKey(result.asItem()) + "_smithing");
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(base), Ingredient.of(material), RecipeCategory.MISC, result.asItem()).unlocks(unlockName, hasItem(material)).save(output, id);
	}
	
	private void conditionalUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String disabledName, boolean isModded)
	{
		ImmutableList<String> disableList = buildDisableList(disabledName, isModded);
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).
			define('#', material).pattern(" # ").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(disableList)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(output);
	}
	
	private void conditionalTowerUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String disabledName, boolean isModded)
	{
		ImmutableList<String> disableList = buildDisableList(disabledName, isModded);
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).define('#', material).
			pattern("###").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(disableList)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(output);
	}
	
	private void conditionalModUpgradeRecipe(ItemLike result, ItemLike baseShield, TagKey<Item> material, String group, String unlockName, String modName)
	{
		ConditionalShapedRecipeBuilder.shaped(result).
			define('O', baseShield).define('#', material).
			pattern(" # ").pattern("#O#").pattern(" # ").
			group(ModSpartanShields.ID + ":" + group).
			condition(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED))).
			condition(new ModLoadedCondition(modName)).
			condition(new NotCondition(new TagEmptyCondition(material.location()))).
			unlockedBy(unlockName, hasItem(material)).
			save(output);
	}
	
	private void mekanismShieldRecipe(ItemLike result, ItemLike baseShield, Item infusedAlloy, Item enrichedDiamond, Item basicControlCircuit, Item energyTablet, TagKey<Item> steelIngot)
	{
		ConditionalShapedRecipeBuilder.shaped(result).
			define('a', infusedAlloy).define('d', enrichedDiamond).define('c', basicControlCircuit).define('b', energyTablet).
			define('#', steelIngot).define('O', baseShield).
			pattern("ada").pattern("bOb").pattern("#c#").
			condition(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED))).
			condition(new ModLoadedCondition(Constants.Mekanism_ModID)).
			unlockedBy("has_energy_tablet", hasItem(energyTablet)).
			save(output);
	}
	
	private void mekanismShieldUpgradeRecipe(ItemLike result, ItemLike baseShield, Item alloy, Item controlCircuit, Item energyTablet, TagKey<Item> steelIngot, String unlockName)
	{
		RecipeOutput conditionedOutput = ((net.neoforged.neoforge.common.extensions.IRecipeOutputExtension) output)
				.withConditions(new TypeDisabledCondition(ImmutableList.of(TypeDisabledCondition.MODDED)), new ModLoadedCondition(Constants.Mekanism_ModID));
		ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.asItem());
		CapturingRecipeOutput capture = new CapturingRecipeOutput();
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.asItem())
			.define('a', alloy).define('c', controlCircuit)
			.define('O', baseShield).define('b', energyTablet).define('#', steelIngot)
			.pattern("a a").pattern("bOb").pattern("#c#")
			.unlockedBy(unlockName, hasItem(baseShield))
			.save(capture, id);
		if (capture.recipe instanceof net.minecraft.world.item.crafting.ShapedRecipe shapedRecipe) {
			conditionedOutput.accept(id, new org.xiyu.spartanshieldsunofficial.item.crafting.PoweredShieldUpgradeRecipe(shapedRecipe), capture.advancement);
		}
	}

	private static class CapturingRecipeOutput implements RecipeOutput
	{
		private net.minecraft.world.item.crafting.Recipe<?> recipe;
		private net.minecraft.advancements.AdvancementHolder advancement;

		@Override
		public net.minecraft.advancements.Advancement.Builder advancement()
		{
			return net.minecraft.advancements.Advancement.Builder.advancement();
		}

		public void includeRootAdvancement()
		{
		}

		@Override
		public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe, net.minecraft.advancements.AdvancementHolder advancement, net.neoforged.neoforge.common.conditions.ICondition... conditions)
		{
			this.recipe = recipe;
			this.advancement = advancement;
		}
		
		@Override
		public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe, net.minecraft.advancements.AdvancementHolder advancement)
		{
			this.recipe = recipe;
			this.advancement = advancement;
		}
	}
	
	private ImmutableList<String> buildDisableList(String disabledName, boolean isModded)
	{
		Builder<String> listBuilder = ImmutableList.<String>builder();
		if(isModded)
			listBuilder.add(TypeDisabledCondition.MODDED);
		listBuilder.add(disabledName);
		return listBuilder.build();
	}
	
	private net.minecraft.advancements.Criterion<InventoryChangeTrigger.TriggerInstance> hasItem(TagKey<Item> tag)
	{
		return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag));
	}
	
	private net.minecraft.advancements.Criterion<InventoryChangeTrigger.TriggerInstance> hasItem(ItemLike item)
	{
		return InventoryChangeTrigger.TriggerInstance.hasItems(item);
	}
	
/*	@Override
	public String getName()
	{
		return ModSpartanShields.NAME + " Recipes";
	}*/
}
