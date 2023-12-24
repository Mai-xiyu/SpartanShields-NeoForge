package com.oblivioussp.spartanshields.item.crafting;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import com.oblivioussp.spartanshields.init.ModRecipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class ShieldBannerRecipeBuilder
{
	private ShieldItem shield;
	
	private ShieldBannerRecipeBuilder(ShieldItem shieldIn)
	{
		shield = shieldIn;
	}
	
	public static ShieldBannerRecipeBuilder recipe(ShieldItem shieldIn)
	{
		return new ShieldBannerRecipeBuilder(shieldIn);
	}
	
	public void save(Consumer<FinishedRecipe> consumerIn)
	{
		ResourceLocation shieldKey = ForgeRegistries.ITEMS.getKey(shield);
		consumerIn.accept(new Result(new ResourceLocation(shieldKey.getNamespace(), shieldKey.getPath() + "_banner"), shield));
	}
	
	public class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final ShieldItem shield;
		
		public Result(ResourceLocation idIn, ShieldItem shieldIn)
		{
			id = idIn;
			shield = shieldIn;
		}

		@Override
		public void serializeRecipeData(JsonObject json) 
		{
			json.addProperty("shield", ForgeRegistries.ITEMS.getKey(shield).toString());
		}

		@Override
		public ResourceLocation getId() 
		{
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() 
		{
			return ModRecipes.SHIELD_BANNER.get();
		}

		@Override
		public JsonObject serializeAdvancement()
		{
			return null;
		}

		@Override
		public ResourceLocation getAdvancementId()
		{
			return null;
		}
		
	}
}
