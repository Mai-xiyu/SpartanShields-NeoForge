package com.oblivioussp.spartanshields.compat.jei;

import com.oblivioussp.spartanshields.ModSpartanShields;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

// TODO: Fix JEI Plugin (or remove it if necessary)
@JeiPlugin
public class SpartanShieldsPlugin implements IModPlugin
{
	private final ResourceLocation PLUGIN_UID = new ResourceLocation(ModSpartanShields.ID, "jei_plugin");
	
//	public static final ResourceLocation SHIELD_BANNER_UID = new ResourceLocation(ModSpartanShields.ID, "shield_banner");
//	public static ShieldBannerRecipeCategory shieldBannerRecipeCategory;
	
	@Override
	public ResourceLocation getPluginUid()
	{
		return PLUGIN_UID;
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) 
	{
/*		shieldBannerRecipeCategory = new ShieldBannerRecipeCategory(reg.getJeiHelpers().getGuiHelper());
		reg.addRecipeCategories(shieldBannerRecipeCategory);*/
	}

	@Override
	public void registerRecipes(IRecipeRegistration reg)
	{
/*		Minecraft mc = Minecraft.getInstance();
		RecipeManager recipeManager = mc.level != null ? Minecraft.getInstance().level.getRecipeManager() : null;
		if(recipeManager == null)
		{
			Log.error("Failed to fetch recipe manager from level: level doesn't exist!");
			return;
		}
		
		List<ShieldDecorationRecipe> recipesToAdd = recipeManager.getAllRecipesFor(RecipeType.CRAFTING).stream().filter(r -> r instanceof ShieldDecorationRecipe).
				map(r -> (ShieldDecorationRecipe)r).filter(r -> r.getSerializer() == RecipeSerializer.SHIELD_DECORATION || r.getSerializer() == ModRecipes.SHIELD_BANNER.get()).
				collect(Collectors.toList());
		
//		reg.addRecipes(recipesToAdd, SHIELD_BANNER_UID);
		reg.addRecipes(ShieldBannerRecipeCategory.RECIPE_TYPE, recipesToAdd);*/
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) 
	{
//		reg.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), ShieldBannerRecipeCategory.RECIPE_TYPE);
	}
	
}
