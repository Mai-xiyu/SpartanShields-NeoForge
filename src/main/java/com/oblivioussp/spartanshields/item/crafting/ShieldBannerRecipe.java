package com.oblivioussp.spartanshields.item.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oblivioussp.spartanshields.init.ModRecipes;
import com.oblivioussp.spartanshields.item.ShieldBaseItem;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ShieldBannerRecipe extends ShieldDecorationRecipe
{
	//private static ResourceLocation NAME = new ResourceLocation(Reference.MOD_ID, "apply_banner");
	protected final Item shieldItem;

	public ShieldBannerRecipe(ResourceLocation loc, Item shield)
	{
		super(loc);
		shieldItem = shield;
	}
	
	@Override
	public boolean matches(CraftingContainer inv, Level level)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i)
        {
            ItemStack itemstack2 = inv.getItem(i);

            if (!itemstack2.isEmpty())
            {
                if (itemstack2.is(ItemTags.BANNERS))
                {
                    if (!itemstack1.isEmpty())
                    {
                        return false;
                    }

                    itemstack1 = itemstack2;
                }
                else
                {
                    if //(!shieldItems.contains(itemstack2.getItem()))
                    	(itemstack2.getItem() != shieldItem)
                    {
                        return false;
                    }

                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    if (itemstack2.getTagElement("BlockEntityTag") != null)
                    {
                        return false;
                    }

                    itemstack = itemstack2;
                }
            }
        }

        if (!itemstack.isEmpty() && !itemstack1.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
	@Override
    public ItemStack assemble(CraftingContainer inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i)
        {
            ItemStack itemstack2 = inv.getItem(i);

            if (!itemstack2.isEmpty())
            {
                if (itemstack2.is(ItemTags.BANNERS))
                {
                    itemstack = itemstack2;
                }
                else if //!shieldItems.contains(itemstack2.getItem()))
                		(itemstack2.getItem() == shieldItem)
                {
                    itemstack1 = itemstack2.copy();
                }
            }
        }

        if (itemstack1.isEmpty())
        {
            return itemstack1;
        }
        else
        {
            CompoundTag tag = itemstack.getTagElement("BlockEntityTag");
            CompoundTag tagCopy = tag == null ? new CompoundTag() : tag.copy();
            tagCopy.putInt("Base", ((BannerItem)itemstack.getItem()).getColor().getId());
            itemstack1.addTagElement("BlockEntityTag", tagCopy);
            return itemstack1;
        }
    }

	@Override
    public ItemStack getResultItem()
    {
        return ItemStack.EMPTY;
    }

	@Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getItem(i);

            if (itemstack.getItem().hasContainerItem(itemstack))
            {
                nonnulllist.set(i, itemstack.getItem().getContainerItem(itemstack));
            }
        }

        return nonnulllist;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
	@Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width * height >= 2;
    }

	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipes.SHIELD_BANNER.get();
	}
	
	public Item getShieldItem()
	{
		return shieldItem;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ShieldBannerRecipe>
	{
		public Serializer()
		{
//			setRegistryName(new ResourceLocation(ModSpartanShields.ID, "apply_banner"));
		}
		
		@Override
		public ShieldBannerRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			String shieldName = GsonHelper.getAsString(json, "shield");
	        Item shieldItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(shieldName));
			if(shieldItem != null)
			{
				if(shieldItem instanceof ShieldBaseItem)
					return new ShieldBannerRecipe(recipeId, shieldItem);
				else
					throw new JsonSyntaxException("Item " + shieldName + " is not a valid Shield!");
			}
			else
	            throw new JsonSyntaxException("Item " + shieldName + " did not exist!");
		}

		@Override
		public ShieldBannerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) 
		{
			ResourceLocation shieldName = buffer.readResourceLocation();
			Item shield = ForgeRegistries.ITEMS.getValue(shieldName);
			return new ShieldBannerRecipe(recipeId, shield);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShieldBannerRecipe recipe) 
		{
			buffer.writeResourceLocation(recipe.shieldItem.getRegistryName());
		}
		
		
	}
}
