package org.xiyu.spartanshieldsunofficial.init;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.crafting.condition.TypeDisabledCondition;
import org.xiyu.spartanshieldsunofficial.item.crafting.PoweredShieldUpgradeRecipe;
import org.xiyu.spartanshieldsunofficial.item.crafting.ShieldBannerRecipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(net.minecraft.core.registries.Registries.RECIPE_SERIALIZER, ModSpartanShields.ID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ModSpartanShields.ID);

    public static final DeferredHolder<RecipeSerializer<?>, ShieldBannerRecipe.Serializer> SHIELD_BANNER = REGISTER.register("apply_banner", ShieldBannerRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, PoweredShieldUpgradeRecipe.Serializer> POWERED_SHIELD_UPGRADE = REGISTER.register("upgrade_powered_shield", PoweredShieldUpgradeRecipe.Serializer::new);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<TypeDisabledCondition>> TYPE_DISABLED_CONDITION = CONDITION_CODECS.register("type_disabled", () -> TypeDisabledCondition.CODEC);
}
