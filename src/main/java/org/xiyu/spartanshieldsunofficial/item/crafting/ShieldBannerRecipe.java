package org.xiyu.spartanshieldsunofficial.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.init.ModRecipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.core.component.DataComponents;

public class ShieldBannerRecipe extends ShieldDecorationRecipe {
    protected final Item shieldItem;

    public ShieldBannerRecipe(CraftingBookCategory category, Item shield) {
        super(category);
        this.shieldItem = shield;
    }

    @Override
    public boolean matches(CraftingInput inv, @NotNull Level level) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack itemstack2 = inv.getItem(i);

            if (!itemstack2.isEmpty()) {
                if (itemstack2.is(ItemTags.BANNERS)) {
                    if (!itemstack1.isEmpty()) {
                        return false;
                    }

                    itemstack1 = itemstack2;
                } else {
                    if //(!shieldItems.contains(itemstack2.getItem()))
                    (itemstack2.getItem() != this.shieldItem) {
                        return false;
                    }

                    if (!itemstack.isEmpty()) {
                        return false;
                    }

                    if (itemstack2.has(DataComponents.BANNER_PATTERNS)) {
                        return false;
                    }

                    itemstack = itemstack2;
                }
            }
        }

        return !itemstack.isEmpty() && !itemstack1.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public @NotNull ItemStack assemble(CraftingInput inv, HolderLookup.@NotNull Provider registries) {
        ItemStack bannerStack = ItemStack.EMPTY;
        ItemStack shieldStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack itemstack2 = inv.getItem(i);

            if (!itemstack2.isEmpty()) {
                if (itemstack2.is(ItemTags.BANNERS)) {
                    bannerStack = itemstack2;
                } else if (itemstack2.getItem() == this.shieldItem) {
                    shieldStack = itemstack2.copy();
                }
            }
        }

        if (!shieldStack.isEmpty()) {
            // Get banner patterns from the banner's data components
            BannerPatternLayers patterns = bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            shieldStack.set(DataComponents.BANNER_PATTERNS, patterns);
            shieldStack.set(DataComponents.BASE_COLOR, ((BannerItem) bannerStack.getItem()).getColor());
        }
        return shieldStack;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);

            if (itemstack.hasCraftingRemainingItem()) {
                nonnulllist.set(i, itemstack.getCraftingRemainingItem());
            }
        }

        return nonnulllist;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.SHIELD_BANNER.get();
    }

    public Item getShieldItem() {
        return this.shieldItem;
    }

    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }

    public static class Serializer implements RecipeSerializer<ShieldBannerRecipe> {
        public static final MapCodec<ShieldBannerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.EQUIPMENT).forGetter(ShieldBannerRecipe::category),
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("shield").forGetter(ShieldBannerRecipe::getShieldItem)
                ).apply(instance, ShieldBannerRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, ShieldBannerRecipe> STREAM_CODEC = StreamCodec.composite(
                CraftingBookCategory.STREAM_CODEC, ShieldBannerRecipe::category,
                ByteBufCodecs.fromCodec(BuiltInRegistries.ITEM.byNameCodec()), ShieldBannerRecipe::getShieldItem,
                ShieldBannerRecipe::new
        );

        public Serializer() {
        }

        @Override
        public @NotNull MapCodec<ShieldBannerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ShieldBannerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
