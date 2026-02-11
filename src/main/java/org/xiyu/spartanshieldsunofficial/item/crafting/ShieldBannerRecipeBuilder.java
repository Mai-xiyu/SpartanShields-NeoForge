package org.xiyu.spartanshieldsunofficial.item.crafting;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public class ShieldBannerRecipeBuilder {
    private final ShieldItem shield;

    private ShieldBannerRecipeBuilder(ShieldItem shieldIn) {
        this.shield = shieldIn;
    }

    public static ShieldBannerRecipeBuilder recipe(ShieldItem shieldIn) {
        return new ShieldBannerRecipeBuilder(shieldIn);
    }

    public void save(RecipeOutput output) {
        ResourceLocation shieldKey = BuiltInRegistries.ITEM.getKey(this.shield);
        ResourceLocation id = ResourceLocation.tryBuild(shieldKey.getNamespace(), shieldKey.getPath() + "_banner");
        output.accept(id, new ShieldBannerRecipe(CraftingBookCategory.EQUIPMENT, this.shield), null);
    }
}
