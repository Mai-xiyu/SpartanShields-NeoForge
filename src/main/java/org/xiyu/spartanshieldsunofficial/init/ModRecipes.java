package org.xiyu.spartanshieldsunofficial.init;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.crafting.condition.TypeDisabledCondition;
import org.xiyu.spartanshieldsunofficial.item.crafting.PoweredShieldUpgradeRecipe;
import org.xiyu.spartanshieldsunofficial.item.crafting.ShieldBannerRecipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModSpartanShields.ID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(ForgeRegistries.Keys.CONDITION_SERIALIZERS, ModSpartanShields.ID);

    public static final RegistryObject<ShieldBannerRecipe.Serializer> SHIELD_BANNER = REGISTER.register("apply_banner", ShieldBannerRecipe.Serializer::new);
    public static final RegistryObject<PoweredShieldUpgradeRecipe.Serializer> POWERED_SHIELD_UPGRADE = REGISTER.register("upgrade_powered_shield", PoweredShieldUpgradeRecipe.Serializer::new);

    public static final RegistryObject<MapCodec<TypeDisabledCondition>> TYPE_DISABLED_CONDITION = CONDITION_CODECS.register("type_disabled", () -> TypeDisabledCondition.CODEC);
}
