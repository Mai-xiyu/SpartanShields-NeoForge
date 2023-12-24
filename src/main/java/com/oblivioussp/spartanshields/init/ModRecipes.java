package com.oblivioussp.spartanshields.init;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.crafting.condition.TypeDisabledCondition;
import com.oblivioussp.spartanshields.item.crafting.PoweredShieldUpgradeRecipe;
import com.oblivioussp.spartanshields.item.crafting.ShieldBannerRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes 
{
	public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModSpartanShields.ID);
	
	public static final RegistryObject<ShieldBannerRecipe.Serializer> SHIELD_BANNER = REGISTER.register("apply_banner", ShieldBannerRecipe.Serializer::new);
	public static final RegistryObject<PoweredShieldUpgradeRecipe.Serializer> POWERED_SHIELD_UPGRADE = REGISTER.register("upgrade_powered_shield", PoweredShieldUpgradeRecipe.Serializer::new);
	
	public static void initConditions()
	{
		CraftingHelper.register(TypeDisabledCondition.Serializer.INSTANCE);
	}
}
