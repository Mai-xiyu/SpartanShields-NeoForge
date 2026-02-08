package org.xiyu.spartanshieldsunofficial.item.crafting;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.NotNull;
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

public class PoweredShieldUpgradeRecipe implements CraftingRecipe {
    private final ShapedRecipe internalRecipe;

    public PoweredShieldUpgradeRecipe(ShapedRecipe baseRecipe) {
        this.internalRecipe = baseRecipe;
    }

    public ShapedRecipe getInternalRecipe() {
        return this.internalRecipe;
    }

    @Override
    public boolean matches(@NotNull CraftingInput inv, @NotNull Level level) {
        return this.internalRecipe.matches(inv, level);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput inv, HolderLookup.@NotNull Provider registries) {
        ItemStack resultStack = this.getResultItem(registries).copy();
        int feToTransfer = 0;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof FEPoweredShieldItem feItem) {
                feToTransfer += feItem.getStoredEnergy(stack);
            }
        }

        int maxFE = 0;
        if (resultStack.getItem() instanceof FEPoweredShieldItem feItem) {
            maxFE = feItem.getFECapacity(resultStack);
        }

        // Clamp the stored FE to the maximum storable energy and store it
        feToTransfer = Mth.clamp(feToTransfer, 0, maxFE);
        if (resultStack.getItem() instanceof FEPoweredShieldItem feItem) {
            feItem.setStoredEnergy(resultStack, feToTransfer);
        }

        return resultStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return this.internalRecipe.canCraftInDimensions(width, height);
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.internalRecipe.getResultItem(registries);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.POWERED_SHIELD_UPGRADE.get();
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingInput inv) {
        return this.internalRecipe.getRemainingItems(inv);
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.internalRecipe.getIngredients();
    }

    @Override
    public boolean isSpecial() {
        return this.internalRecipe.isSpecial();
    }

    @Override
    public @NotNull String getGroup() {
        return this.internalRecipe.getGroup();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return this.internalRecipe.getToastSymbol();
    }

    public static class Serializer implements RecipeSerializer<PoweredShieldUpgradeRecipe> {
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

        public Serializer() {
        }

        @Override
        public @NotNull MapCodec<PoweredShieldUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, PoweredShieldUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }
}
