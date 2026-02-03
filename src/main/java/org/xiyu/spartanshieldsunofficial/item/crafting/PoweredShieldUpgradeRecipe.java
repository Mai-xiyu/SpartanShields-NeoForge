package org.xiyu.spartanshieldsunofficial.item.crafting;

import com.mojang.serialization.MapCodec;
import org.xiyu.spartanshieldsunofficial.init.ModRecipes;
import org.xiyu.spartanshieldsunofficial.item.FEPoweredShieldItem;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class PoweredShieldUpgradeRecipe implements CraftingRecipe
{
	private final ShapedRecipe internalRecipe;
	
	public PoweredShieldUpgradeRecipe(ShapedRecipe baseRecipe)
	{
		internalRecipe = baseRecipe;
	}
	
	public ShapedRecipe getInternalRecipe()
	{
		return internalRecipe;
	}

	@Override
	public boolean matches(CraftingInput inv, Level level) 
	{
		return internalRecipe.matches(inv, level);
	}

	@Override
	public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registries) 
	{
		ItemStack resultStack = getResultItem(registries).copy();
		int feToTransfer = 0;
		
		for(int i = 0; i < inv.size(); i++)
		{
			ItemStack stack = inv.getItem(i);
			if(stack.getItem() instanceof FEPoweredShieldItem feItem)
			{
				feToTransfer += feItem.getStoredEnergy(stack);
			}
		}
		
		int maxFE = 0;
		if(resultStack.getItem() instanceof FEPoweredShieldItem feItem)
		{
			maxFE = feItem.getFECapacity(resultStack);
		}
		
		// Clamp the stored FE to the maximum storable energy and store it
		feToTransfer = Mth.clamp(feToTransfer, 0, maxFE);
		if(resultStack.getItem() instanceof FEPoweredShieldItem feItem)
		{
			feItem.setStoredEnergy(resultStack, feToTransfer);
		}
		
		return resultStack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return internalRecipe.canCraftInDimensions(width, height);
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registries) 
	{
		return internalRecipe.getResultItem(registries);
	}

	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipes.POWERED_SHIELD_UPGRADE.get();
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput inv)
	{
		return internalRecipe.getRemainingItems(inv);
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() 
	{
		return internalRecipe.getIngredients();
	}
	
	@Override
	public boolean isSpecial() 
	{
		return internalRecipe.isSpecial();
	}
	
	@Override
	public String getGroup() 
	{
		return internalRecipe.getGroup();
	}
	
	@Override
	public ItemStack getToastSymbol() 
	{
		return internalRecipe.getToastSymbol();
	}
	
	public static class Serializer implements RecipeSerializer<PoweredShieldUpgradeRecipe>
	{
		public static final MapCodec<PoweredShieldUpgradeRecipe> CODEC = 
			RecipeSerializer.SHAPED_RECIPE.codec().xmap(
				PoweredShieldUpgradeRecipe::new,
				PoweredShieldUpgradeRecipe::getInternalRecipe
			);
		
		public static final StreamCodec<RegistryFriendlyByteBuf, PoweredShieldUpgradeRecipe> STREAM_CODEC =
			RecipeSerializer.SHAPED_RECIPE.streamCodec().map(
				PoweredShieldUpgradeRecipe::new,
				PoweredShieldUpgradeRecipe::getInternalRecipe
			);
		
		public Serializer() {}

		@Override
		public MapCodec<PoweredShieldUpgradeRecipe> codec()
		{
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PoweredShieldUpgradeRecipe> streamCodec()
		{
			return STREAM_CODEC;
		}
	}

	@Override
	public CraftingBookCategory category() 
	{
		return CraftingBookCategory.EQUIPMENT;
	}
}
