package com.oblivioussp.spartanshields.data;

import org.jetbrains.annotations.Nullable;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.init.ModItems;
import com.oblivioussp.spartanshields.tags.ModItemTags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider
{

	public ModItemTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(generator, new BlockTagsProvider(generator, ModSpartanShields.ID, existingFileHelper)
			{
				@Override
				protected void addTags() {}
			}, ModSpartanShields.ID, existingFileHelper);
	}
	
	@Override
	protected void addTags()
	{
		tag(ModItemTags.TIN_INGOT);
		tag(ModItemTags.BRONZE_INGOT);
		tag(ModItemTags.STEEL_INGOT);
		tag(ModItemTags.SILVER_INGOT);
		tag(ModItemTags.ELECTRUM_INGOT);
		tag(ModItemTags.LEAD_INGOT);
		tag(ModItemTags.NICKEL_INGOT);
		tag(ModItemTags.INVAR_INGOT);
		tag(ModItemTags.CONSTANTAN_INGOT);
		tag(ModItemTags.PLATINUM_INGOT);
		tag(ModItemTags.ALUMINUM_INGOT);
		
		tag(ModItemTags.MANASTEEL_INGOT);
		tag(ModItemTags.TERRASTEEL_INGOT);
		tag(ModItemTags.ELEMENTIUM_INGOT);
		tag(ModItemTags.OSMIUM_INGOT);
		tag(ModItemTags.REFINED_GLOWSTONE_INGOT);
		tag(ModItemTags.REFINED_OBSIDIAN_INGOT);
		tag(ModItemTags.SIGNALUM_INGOT);
		tag(ModItemTags.LUMIUM_INGOT);
		tag(ModItemTags.ENDERIUM_INGOT);
		
		tag(ModItemTags.BASIC_SHIELDS).add(ModItems.WOODEN_BASIC_SHIELD.get(), ModItems.STONE_BASIC_SHIELD.get(), ModItems.COPPER_BASIC_SHIELD.get() ,ModItems.IRON_BASIC_SHIELD.get(), ModItems.GOLDEN_BASIC_SHIELD.get(), ModItems.DIAMOND_BASIC_SHIELD.get(), ModItems.NETHERITE_BASIC_SHIELD.get(), ModItems.OBSIDIAN_BASIC_SHIELD.get(),
				ModItems.TIN_BASIC_SHIELD.get(), ModItems.BRONZE_BASIC_SHIELD.get(), ModItems.STEEL_BASIC_SHIELD.get(), ModItems.SILVER_BASIC_SHIELD.get(), ModItems.ELECTRUM_BASIC_SHIELD.get(), ModItems.LEAD_BASIC_SHIELD.get(), ModItems.NICKEL_BASIC_SHIELD.get(), ModItems.INVAR_BASIC_SHIELD.get(), ModItems.CONSTANTAN_BASIC_SHIELD.get(), ModItems.PLATINUM_BASIC_SHIELD.get(),
				ModItems.ALUMINUM_BASIC_SHIELD.get(), ModItems.SIGNALUM_BASIC_SHIELD.get(), ModItems.LUMIUM_BASIC_SHIELD.get(), ModItems.ENDERIUM_BASIC_SHIELD.get(), ModItems.MANASTEEL_BASIC_SHIELD.get(), ModItems.TERRASTEEL_BASIC_SHIELD.get(), ModItems.ELEMENTIUM_BASIC_SHIELD.get(), ModItems.OSMIUM_BASIC_SHIELD.get(), ModItems.LAPIS_BASIC_SHIELD.get(), ModItems.REFINED_GLOWSTONE_BASIC_SHIELD.get(), ModItems.REFINED_OBSIDIAN_BASIC_SHIELD.get(),
				ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get());
		
		tag(ModItemTags.TOWER_SHIELDS).add(ModItems.WOODEN_TOWER_SHIELD.get(), ModItems.STONE_TOWER_SHIELD.get(), ModItems.COPPER_TOWER_SHIELD.get() ,ModItems.IRON_TOWER_SHIELD.get(), ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(), ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.OBSIDIAN_TOWER_SHIELD.get(),
				ModItems.TIN_TOWER_SHIELD.get(), ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.STEEL_TOWER_SHIELD.get(), ModItems.SILVER_TOWER_SHIELD.get(), ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.LEAD_TOWER_SHIELD.get(), ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.INVAR_TOWER_SHIELD.get(), ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.PLATINUM_TOWER_SHIELD.get(),
				ModItems.ALUMINUM_TOWER_SHIELD.get(), ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get(),
				ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get()).addOptional(new ResourceLocation("mekanismtools:bronze_shield")).addOptional(new ResourceLocation("mekanismtools:steel_shield")).
				addOptional(new ResourceLocation("mekanismtools:lapis_lazuli_shield")).addOptional(new ResourceLocation("mekanismtools:osmium_shield")).addOptional(new ResourceLocation("mekanismtools:refined_glowstone_shield")).addOptional(new ResourceLocation("mekanismtools:refined_obsidian_shield"));
		
		tag(ModItemTags.SHIELDS_WITH_BASH).add(Items.SHIELD).addTag(ModItemTags.BASIC_SHIELDS).addTag(ModItemTags.TOWER_SHIELDS);
		
		tag(ModItemTags.MANA_USING_ITEMS).add(ModItems.MANASTEEL_BASIC_SHIELD.get(), ModItems.TERRASTEEL_BASIC_SHIELD.get(), ModItems.ELEMENTIUM_BASIC_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get());
	}
	
	@Override
	protected final void copy(TagKey<Block> p_206422_, TagKey<Item> p_206423_) 
	{
		throw new IllegalArgumentException("Cannot perform copy operations with Block tags!");
	}

	@Override
	public String getName() 
	{
		return ModSpartanShields.NAME + " Item Tags";
	}
}
