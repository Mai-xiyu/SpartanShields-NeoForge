package com.oblivioussp.spartanshields.item.crafting;

import com.google.gson.JsonObject;
import com.oblivioussp.spartanshields.init.ModRecipes;
import com.oblivioussp.spartanshields.item.FEPoweredShieldItem;
import com.oblivioussp.spartanshields.util.Log;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PoweredShieldUpgradeRecipe implements CraftingRecipe, IShapedRecipe<CraftingContainer>
{
//	public static final NonNullSupplier<IllegalArgumentException> CAPABILITY_EXCEPTION = () -> new IllegalArgumentException("Capability must not be null!");
	
	private final ShapedRecipe internalRecipe;
	
	public PoweredShieldUpgradeRecipe(ShapedRecipe baseRecipe)
	{
		internalRecipe = baseRecipe;
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) 
	{
		return internalRecipe.matches(inv, level);
	}

	@Override
	public ItemStack assemble(CraftingContainer inv) 
	{
		ItemStack resultStack = getResultItem().copy();
		//CompoundNBT nbt = new CompoundNBT();
		int feToTransfer = 0;
		
		for(int i = 0; i < inv.getContainerSize(); i++)
		{
			ItemStack stack = inv.getItem(i);
			LazyOptional<IEnergyStorage> cap = stack.getCapability(CapabilityEnergy.ENERGY);
			if(cap.isPresent())
				feToTransfer += cap.resolve().orElseThrow().getEnergyStored();
		}
		
		// The Result item *MUST* have the energy capability or it will cause a crash.
		// It is used to look up the maximum stored FE.
		LazyOptional<IEnergyStorage> resultCap = resultStack.getCapability(CapabilityEnergy.ENERGY);
		int maxFE = resultCap.resolve().orElseThrow().getMaxEnergyStored();
		
		// Clamp the stored FE to the maximum storable energy and store it in NBT 
		// (more for edge-cases; in case a recipe is changed that adds more energy tablets)
		feToTransfer = Mth.clamp(feToTransfer, 0, maxFE);
		resultStack.getOrCreateTag().putInt(FEPoweredShieldItem.NBT_ENERGY, feToTransfer);
		
		return resultStack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return internalRecipe.canCraftInDimensions(width, height);
	}

	@Override
	public ItemStack getResultItem() 
	{
		return internalRecipe.getResultItem();
	}

	@Override
	public ResourceLocation getId() 
	{
		return internalRecipe.getId();
	}

	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipes.POWERED_SHIELD_UPGRADE.get();
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv)
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

	@Override
	public int getRecipeWidth() 
	{
		return internalRecipe.getRecipeWidth();
	}

	@Override
	public int getRecipeHeight() 
	{
		return internalRecipe.getRecipeHeight();
	}
	
	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PoweredShieldUpgradeRecipe>
	{
		public Serializer()
		{
//			setRegistryName(new ResourceLocation(ModSpartanShields.ID, "upgrade_powered_shield"));
		}

		@Override
		public PoweredShieldUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) 
		{
			return new PoweredShieldUpgradeRecipe(RecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, json));
		}

		@Override
		public PoweredShieldUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			try
			{
				return new PoweredShieldUpgradeRecipe(RecipeSerializer.SHAPED_RECIPE.fromNetwork(recipeId, buffer));
			}
			catch(Exception e)
			{
				Log.error("Failed to read a Powered Shield Upgrade Recipe from a packet!");
				throw e;
			}
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, PoweredShieldUpgradeRecipe recipe)
		{
			try
			{
				RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe.internalRecipe);
			}
			catch(Exception e)
			{
				Log.error("Failed to write a Powered Shield Upgrade Recipe to a packet!");
			}
		}
		
	}
}
