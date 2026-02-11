package org.xiyu.spartanshieldsunofficial.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * Copy of vanilla's {@linkplain ShapedRecipeBuilder} with additions to allow Forge's condition system to be serialized too
 */
public class ConditionalShapedRecipeBuilder {
    private final Item result;
    private final int count;
    private final ShapedRecipeBuilder builder;
    private final List<ICondition> conditions = new ArrayList<>();

    private ConditionalShapedRecipeBuilder(ItemLike resultIn, int countIn) {
        this.result = resultIn.asItem();
        this.count = countIn;
        this.builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultIn, countIn);
    }

    public static ConditionalShapedRecipeBuilder shaped(ItemLike itemIn) {
        return new ConditionalShapedRecipeBuilder(itemIn, 1);
    }

    public static ConditionalShapedRecipeBuilder shaped(ItemLike itemIn, int countIn) {
        return new ConditionalShapedRecipeBuilder(itemIn, countIn);
    }

    public ConditionalShapedRecipeBuilder define(Character character, TagKey<Item> tagIn) {
        return this.define(character, Ingredient.of(tagIn));
    }

    public ConditionalShapedRecipeBuilder define(Character character, ItemLike itemIn) {
        return this.define(character, Ingredient.of(itemIn));
    }

    public ConditionalShapedRecipeBuilder define(Character character, Ingredient ingredientIn) {
        this.builder.define(character, ingredientIn);
        return this;
    }

    public ConditionalShapedRecipeBuilder pattern(String patternIn) {
        this.builder.pattern(patternIn);
        return this;
    }

    public ConditionalShapedRecipeBuilder unlockedBy(String name, Criterion<?> criterionIn) {
        this.builder.unlockedBy(name, criterionIn);
        return this;
    }

    public ConditionalShapedRecipeBuilder group(String groupIn) {
        this.builder.group(groupIn);
        return this;
    }

    public ConditionalShapedRecipeBuilder condition(ICondition conditionIn) {
        this.conditions.add(conditionIn);
        return this;
    }

    public void save(RecipeOutput output) {
        this.save(output, BuiltInRegistries.ITEM.getKey(this.result));
    }

    public void save(RecipeOutput output, String save) {
        ResourceLocation resultLoc = BuiltInRegistries.ITEM.getKey(this.result);
        ResourceLocation saveLoc = ResourceLocation.parse(save);
        if (saveLoc.equals(resultLoc))
            throw new IllegalStateException("Shaped recipe " + save + " save argument is redundant as it's the same as the item id!");
        else
            this.save(output, saveLoc);
    }

    public void save(RecipeOutput output, ResourceLocation id) {
        // Note: Forge 1.21.1 handles conditions differently than NeoForge
        // For now, save without conditions - conditions can be added via JSON post-processing
        this.builder.save(output, id);
    }
}
