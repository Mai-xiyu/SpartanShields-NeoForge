package com.oblivioussp.spartanshields.data;

import java.util.List;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.init.ModItems;
import com.oblivioussp.spartanshields.util.Constants;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider 
{
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) 
	{
		super(generator, ModSpartanShields.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() 
	{
		List.of(ModItems.WOODEN_BASIC_SHIELD, ModItems.STONE_BASIC_SHIELD, ModItems.COPPER_BASIC_SHIELD, ModItems.IRON_BASIC_SHIELD, ModItems.GOLDEN_BASIC_SHIELD, ModItems.DIAMOND_BASIC_SHIELD, ModItems.NETHERITE_BASIC_SHIELD, ModItems.OBSIDIAN_BASIC_SHIELD,
				ModItems.TIN_BASIC_SHIELD, ModItems.BRONZE_BASIC_SHIELD, ModItems.STEEL_BASIC_SHIELD, ModItems.SILVER_BASIC_SHIELD, ModItems.ELECTRUM_BASIC_SHIELD, ModItems.LEAD_BASIC_SHIELD, ModItems.NICKEL_BASIC_SHIELD, ModItems.INVAR_BASIC_SHIELD, ModItems.CONSTANTAN_BASIC_SHIELD,
				ModItems.PLATINUM_BASIC_SHIELD, ModItems.ALUMINUM_BASIC_SHIELD, ModItems.SIGNALUM_BASIC_SHIELD, ModItems.LUMIUM_BASIC_SHIELD, ModItems.ENDERIUM_BASIC_SHIELD)
			.forEach((shield) -> createBasicShieldModel(shield.get()));
		
		// Botania Basic Shields
		List.of(ModItems.MANASTEEL_BASIC_SHIELD, ModItems.TERRASTEEL_BASIC_SHIELD, ModItems.ELEMENTIUM_BASIC_SHIELD)
			.forEach((shield) -> createModdedBasicShieldModel(shield.get(), Constants.Botania_ModID));

		// Mekanism Basic Shields
		List.of(ModItems.LAPIS_BASIC_SHIELD, ModItems.OSMIUM_BASIC_SHIELD, ModItems.REFINED_GLOWSTONE_BASIC_SHIELD, ModItems.REFINED_OBSIDIAN_BASIC_SHIELD)
			.forEach((shield) -> createModdedBasicShieldModel(shield.get(), Constants.Mekanism_ModID));
		// Mekanism Powered Basic Shields
		List.of(ModItems.BASIC_MEKANISTS_BASIC_SHIELD, ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD, ModItems.ELITE_MEKANISTS_BASIC_SHIELD, ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD)
			.forEach((shield) -> createMekanismPoweredBasicShieldModel(shield.get()));
		
		// Tower Shields
		List.of(ModItems.WOODEN_TOWER_SHIELD, ModItems.STONE_TOWER_SHIELD, ModItems.COPPER_TOWER_SHIELD, ModItems.IRON_TOWER_SHIELD, ModItems.GOLDEN_TOWER_SHIELD, ModItems.DIAMOND_TOWER_SHIELD, ModItems.NETHERITE_TOWER_SHIELD, ModItems.OBSIDIAN_TOWER_SHIELD,
				ModItems.TIN_TOWER_SHIELD, ModItems.BRONZE_TOWER_SHIELD, ModItems.STEEL_TOWER_SHIELD, ModItems.SILVER_TOWER_SHIELD, ModItems.ELECTRUM_TOWER_SHIELD, ModItems.LEAD_TOWER_SHIELD, ModItems.NICKEL_TOWER_SHIELD, ModItems.INVAR_TOWER_SHIELD, ModItems.CONSTANTAN_TOWER_SHIELD,
				ModItems.PLATINUM_TOWER_SHIELD, ModItems.ALUMINUM_TOWER_SHIELD, ModItems.SIGNALUM_TOWER_SHIELD, ModItems.LUMIUM_TOWER_SHIELD, ModItems.ENDERIUM_TOWER_SHIELD,
				ModItems.MANASTEEL_TOWER_SHIELD, ModItems.TERRASTEEL_TOWER_SHIELD, ModItems.ELEMENTIUM_TOWER_SHIELD,
				ModItems.BASIC_MEKANISTS_TOWER_SHIELD, ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD, ModItems.ELITE_MEKANISTS_TOWER_SHIELD, ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD)
			.forEach((shield) -> createTowerShieldModel(shield.get()));
	}
	
	protected ResourceLocation createBasicShieldModel(Item itemIn)
	{
		String itemPath = ForgeRegistries.ITEMS.getKey(itemIn).getPath();
		ModelFile blockingModel = withExistingParent(itemPath + "_blocking", new ResourceLocation(ModSpartanShields.ID, "basic_shield_base_blocking"))
				.texture("layer0", "item/" + itemPath);
		return withExistingParent(itemPath, new ResourceLocation(ModSpartanShields.ID, "basic_shield_base"))
				.texture("layer0", "item/" + itemPath)
				.override().predicate(new ResourceLocation("blocking"), 1.0f).model(blockingModel).end()
				.getLocation();
	}
	
	protected ResourceLocation createModdedBasicShieldModel(Item itemIn, String modId)
	{
		String itemPath = ForgeRegistries.ITEMS.getKey(itemIn).getPath();
		ModelFile blockingModel = withExistingParent(itemPath + "_blocking", new ResourceLocation(ModSpartanShields.ID, "basic_shield_base_blocking"))
				.texture("layer0", "item/" + modId + "/" + itemPath);
		return withExistingParent(itemPath, new ResourceLocation(ModSpartanShields.ID, "basic_shield_base"))
				.texture("layer0", "item/" + modId + "/" + itemPath)
				.override().predicate(new ResourceLocation("blocking"), 1.0f).model(blockingModel).end()
				.getLocation();
	}
	
	protected ResourceLocation createMekanismPoweredBasicShieldModel(Item itemIn)
	{
		String itemPath = ForgeRegistries.ITEMS.getKey(itemIn).getPath();
		ModelFile blockingModel = getExistingFile(new ResourceLocation(ModSpartanShields.ID, "mekanists_basic_shield_base_blocking"));
		ModelFile deactivatedModel = getExistingFile(new ResourceLocation(ModSpartanShields.ID, "mekanists_basic_shield_base_deactivated"));
		ModelFile deactivatedBlockingModel = getExistingFile(new ResourceLocation(ModSpartanShields.ID, "mekanists_basic_shield_base_deactivated_blocking"));
		ResourceLocation disabled = new ResourceLocation("disabled");
		ResourceLocation blocking = new ResourceLocation("blocking");
		return withExistingParent(itemPath, new ResourceLocation(ModSpartanShields.ID, "mekanists_basic_shield_base"))
				.override().predicate(disabled, 0.0f).predicate(blocking, 1.0f).model(blockingModel).end()
				.override().predicate(disabled, 1.0f).predicate(blocking, 0.0f).model(deactivatedModel).end()
				.override().predicate(disabled, 1.0f).predicate(blocking, 1.0f).model(deactivatedBlockingModel).end()
				.getLocation();
	}
	
	protected ResourceLocation createTowerShieldModel(Item itemIn)
	{
		String itemPath = ForgeRegistries.ITEMS.getKey(itemIn).getPath();
		ModelFile blockingModel = getExistingFile(new ResourceLocation(ModSpartanShields.ID, "tower_shield_base_blocking"));
		return withExistingParent(itemPath, new ResourceLocation(ModSpartanShields.ID, "tower_shield_base"))
				.override().predicate(new ResourceLocation("blocking"), 1.0f).model(blockingModel).end()
				.getLocation();
	}

	@Override
	public String getName()
	{
		return ModSpartanShields.NAME + " Item Models";
	}
}
