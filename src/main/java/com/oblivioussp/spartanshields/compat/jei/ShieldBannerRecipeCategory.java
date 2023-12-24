package com.oblivioussp.spartanshields.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.item.crafting.ShieldBannerRecipe;

import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;

public class ShieldBannerRecipeCategory implements IRecipeCategory<ShieldDecorationRecipe> 
{
	private static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");
	public static final RecipeType<ShieldDecorationRecipe> RECIPE_TYPE = RecipeType.create(ModSpartanShields.ID, "shield_banner", ShieldDecorationRecipe.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	private final String titleKey;
	
	public ShieldBannerRecipeCategory(IGuiHelper guiHelper)
	{
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.SHIELD));
		background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 60, 116, 54);
		titleKey = "gui." + ModSpartanShields.ID + ".category.shield_banner";
	}
	
	@Override
	public RecipeType<ShieldDecorationRecipe> getRecipeType() 
	{
		return RECIPE_TYPE;
	}

//	@Deprecated
	@Override
	public ResourceLocation getUid()
	{
		return null;
	}

//	@Deprecated
	@Override
	public Class<? extends ShieldDecorationRecipe> getRecipeClass() 
	{
		return null;
	}

	@Override
	public Component getTitle() 
	{
		return new TranslatableComponent(titleKey);
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public IDrawable getIcon()
	{
		return icon;
	}
	
	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ShieldDecorationRecipe recipe, IFocusGroup focuses) 
	{
		ItemStack shield = new ItemStack(Items.SHIELD);
		List<ItemStack> banners = Arrays.asList(Ingredient.of(ItemTags.BANNERS).getItems());
		
		if(recipe instanceof ShieldBannerRecipe)
		{
			ShieldBannerRecipe shieldRecipe = (ShieldBannerRecipe)recipe;
			shield = new ItemStack(shieldRecipe.getShieldItem());
		}
		
		List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		inputs.add(Collections.singletonList(shield));
		inputs.add(banners);
//		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		
		for(ItemStack banner : banners)
		{
			ItemStack banneredShield = shield.copy();
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("Base", ((BannerItem)banner.getItem()).getColor().getId());
			banneredShield.getOrCreateTag().put("BlockEntityTag", nbt);
			outputs.add(banneredShield);
		}
		
//		ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(outputs));
		
		builder.setShapeless();
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredient(VanillaTypes.ITEM_STACK, shield);
		IRecipeSlotBuilder bannerSlot = builder.addSlot(RecipeIngredientRole.INPUT, 19, 1).addIngredients(VanillaTypes.ITEM_STACK, banners);
		IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19).addIngredients(VanillaTypes.ITEM_STACK, outputs)
				.addTooltipCallback((recipeSlotView, tooltip) -> {
					if(outputs.get(0).is(recipeSlotView.getDisplayedIngredient(VanillaTypes.ITEM_STACK).get().getItem()))
					{
						tooltip.add(new TranslatableComponent("gui." + ModSpartanShields.ID + ".tooltip.shield_banner.any_pattern").withStyle(ChatFormatting.ITALIC, ChatFormatting.RED));
					}
				});
		
		builder.createFocusLink(bannerSlot, outputSlot);
	}

	/*@Override
	public void setIngredients(ShieldDecorationRecipe recipe, IIngredients ingredients) 
	{
		ItemStack shield = new ItemStack(Items.SHIELD);
		List<ItemStack> banners = Arrays.asList(Ingredient.of(ItemTags.BANNERS).getItems());
		
		if(recipe instanceof ShieldBannerRecipe)
		{
			ShieldBannerRecipe shieldRecipe = (ShieldBannerRecipe)recipe;
			shield = new ItemStack(shieldRecipe.getShieldItem());
		}
		
		List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		inputs.add(Collections.singletonList(shield));
		inputs.add(banners);
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		
		for(ItemStack banner : banners)
		{
			ItemStack banneredShield = shield.copy();
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("Base", ((BannerItem)banner.getItem()).getColor().getId());
			banneredShield.getOrCreateTag().put("BlockEntityTag", nbt);
			outputs.add(banneredShield);
		}
		
		ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(outputs));
	}*/

	/*@Override
	public void setRecipe(IRecipeLayout recipeLayout, ShieldDecorationRecipe recipe, IIngredients ingredients) 
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, false, 94, 18);
		
		guiItemStacks.init(1, true, 18, 18);
		guiItemStacks.init(2, true, 36, 18);
		
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
		
		guiItemStacks.set(1, inputs.get(0));
		guiItemStacks.set(2, inputs.get(1));
		guiItemStacks.set(0, outputs.get(0));
		
		recipeLayout.setShapeless();
		IGuiItemStackGroup group = recipeLayout.getItemStacks();
		group.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if(outputs.get(0).contains(ingredient))
			{
				tooltip.add(new TranslatableComponent("gui." + ModSpartanShields.ID + ".tooltip.shield_banner.any_pattern").withStyle(ChatFormatting.ITALIC, ChatFormatting.RED));
			}
		});
	}*/
	
	

}
