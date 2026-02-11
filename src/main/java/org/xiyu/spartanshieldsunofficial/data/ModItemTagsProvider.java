package org.xiyu.spartanshieldsunofficial.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.tags.ModItemTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> holderProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, holderProvider, (item) ->
                BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow(), ModSpartanShields.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModItemTags.TIN_INGOT);
        this.tag(ModItemTags.BRONZE_INGOT);
        this.tag(ModItemTags.STEEL_INGOT);
        this.tag(ModItemTags.SILVER_INGOT);
        this.tag(ModItemTags.ELECTRUM_INGOT);
        this.tag(ModItemTags.LEAD_INGOT);
        this.tag(ModItemTags.NICKEL_INGOT);
        this.tag(ModItemTags.INVAR_INGOT);
        this.tag(ModItemTags.CONSTANTAN_INGOT);
        this.tag(ModItemTags.PLATINUM_INGOT);
        this.tag(ModItemTags.ALUMINUM_INGOT);

        this.tag(ModItemTags.MANASTEEL_INGOT);
        this.tag(ModItemTags.TERRASTEEL_INGOT);
        this.tag(ModItemTags.ELEMENTIUM_INGOT);
        this.tag(ModItemTags.OSMIUM_INGOT);
        this.tag(ModItemTags.REFINED_GLOWSTONE_INGOT);
        this.tag(ModItemTags.REFINED_OBSIDIAN_INGOT);
        this.tag(ModItemTags.SIGNALUM_INGOT);
        this.tag(ModItemTags.LUMIUM_INGOT);
        this.tag(ModItemTags.ENDERIUM_INGOT);

        this.tag(ModItemTags.BASIC_SHIELDS).add(ModItems.WOODEN_BASIC_SHIELD.get(), ModItems.STONE_BASIC_SHIELD.get(), ModItems.COPPER_BASIC_SHIELD.get(), ModItems.IRON_BASIC_SHIELD.get(), ModItems.GOLDEN_BASIC_SHIELD.get(), ModItems.DIAMOND_BASIC_SHIELD.get(), ModItems.NETHERITE_BASIC_SHIELD.get(), ModItems.OBSIDIAN_BASIC_SHIELD.get(),
                ModItems.TIN_BASIC_SHIELD.get(), ModItems.BRONZE_BASIC_SHIELD.get(), ModItems.STEEL_BASIC_SHIELD.get(), ModItems.SILVER_BASIC_SHIELD.get(), ModItems.ELECTRUM_BASIC_SHIELD.get(), ModItems.LEAD_BASIC_SHIELD.get(), ModItems.NICKEL_BASIC_SHIELD.get(), ModItems.INVAR_BASIC_SHIELD.get(), ModItems.CONSTANTAN_BASIC_SHIELD.get(), ModItems.PLATINUM_BASIC_SHIELD.get(),
                ModItems.ALUMINUM_BASIC_SHIELD.get(), ModItems.SIGNALUM_BASIC_SHIELD.get(), ModItems.LUMIUM_BASIC_SHIELD.get(), ModItems.ENDERIUM_BASIC_SHIELD.get(), ModItems.MANASTEEL_BASIC_SHIELD.get(), ModItems.TERRASTEEL_BASIC_SHIELD.get(), ModItems.ELEMENTIUM_BASIC_SHIELD.get(), ModItems.OSMIUM_BASIC_SHIELD.get(), ModItems.LAPIS_BASIC_SHIELD.get(), ModItems.REFINED_GLOWSTONE_BASIC_SHIELD.get(), ModItems.REFINED_OBSIDIAN_BASIC_SHIELD.get(),
                ModItems.BASIC_MEKANISTS_BASIC_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_BASIC_SHIELD.get(), ModItems.ELITE_MEKANISTS_BASIC_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_BASIC_SHIELD.get(), ModItems.DARK_STEEL_RIOT_BASIC_SHIELD.get());

        this.tag(ModItemTags.TOWER_SHIELDS).add(ModItems.WOODEN_TOWER_SHIELD.get(), ModItems.STONE_TOWER_SHIELD.get(), ModItems.COPPER_TOWER_SHIELD.get(), ModItems.IRON_TOWER_SHIELD.get(), ModItems.GOLDEN_TOWER_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(), ModItems.NETHERITE_TOWER_SHIELD.get(), ModItems.OBSIDIAN_TOWER_SHIELD.get(),
                        ModItems.TIN_TOWER_SHIELD.get(), ModItems.BRONZE_TOWER_SHIELD.get(), ModItems.STEEL_TOWER_SHIELD.get(), ModItems.SILVER_TOWER_SHIELD.get(), ModItems.ELECTRUM_TOWER_SHIELD.get(), ModItems.LEAD_TOWER_SHIELD.get(), ModItems.NICKEL_TOWER_SHIELD.get(), ModItems.INVAR_TOWER_SHIELD.get(), ModItems.CONSTANTAN_TOWER_SHIELD.get(), ModItems.PLATINUM_TOWER_SHIELD.get(),
                        ModItems.ALUMINUM_TOWER_SHIELD.get(), ModItems.SIGNALUM_TOWER_SHIELD.get(), ModItems.LUMIUM_TOWER_SHIELD.get(), ModItems.ENDERIUM_TOWER_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get(),
                        ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get(), ModItems.DARK_STEEL_RIOT_TOWER_SHIELD.get()).addOptional(ResourceLocation.parse("mekanismtools:bronze_shield")).addOptional(ResourceLocation.parse("mekanismtools:steel_shield")).
                addOptional(ResourceLocation.parse("mekanismtools:lapis_lazuli_shield")).addOptional(ResourceLocation.parse("mekanismtools:osmium_shield")).addOptional(ResourceLocation.parse("mekanismtools:refined_glowstone_shield")).addOptional(ResourceLocation.parse("mekanismtools:refined_obsidian_shield"));

        this.tag(ModItemTags.SHIELDS_WITH_BASH).add(Items.SHIELD).addTag(ModItemTags.BASIC_SHIELDS).addTag(ModItemTags.TOWER_SHIELDS);

        this.tag(ModItemTags.MANA_USING_ITEMS).add(ModItems.MANASTEEL_BASIC_SHIELD.get(), ModItems.TERRASTEEL_BASIC_SHIELD.get(), ModItems.ELEMENTIUM_BASIC_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get());
    }

    @Override
    public @NotNull String getName() {
        return ModSpartanShields.NAME + " Item Tags";
    }
}
