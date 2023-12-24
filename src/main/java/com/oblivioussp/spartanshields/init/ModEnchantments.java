package com.oblivioussp.spartanshields.init;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.enchantment.FirebrandEnchantment;
import com.oblivioussp.spartanshields.enchantment.PaybackEnchantment;
import com.oblivioussp.spartanshields.enchantment.SpikesEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments
{
	public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ModSpartanShields.ID);
	
	public static final RegistryObject<Enchantment> SPIKES = REGISTER.register("spikes", () -> new SpikesEnchantment(Rarity.UNCOMMON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
	public static final RegistryObject<Enchantment> FIREBRAND = REGISTER.register("firebrand", () -> new FirebrandEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
	public static final RegistryObject<Enchantment> PAYBACK = REGISTER.register("payback", () -> new PaybackEnchantment(Rarity.VERY_RARE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
	
}
