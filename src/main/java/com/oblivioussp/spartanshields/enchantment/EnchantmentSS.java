package com.oblivioussp.spartanshields.enchantment;

import com.oblivioussp.spartanshields.item.ShieldBaseItem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class EnchantmentSS extends Enchantment
{
	public static EnchantmentCategory TYPE_SHIELD = EnchantmentCategory.create("ss_shield", (item) -> item instanceof ShieldBaseItem);
	
	protected EnchantmentSS(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
	}
	
	/**
	 * Called when a user with the enchanted item is attacked (regardless if the user is blocking)
	 * Allows the application of Spikes when the user is blocking.
	 * @param user
	 * @param attacker
	 * @param level
	 */
	public abstract void onUserAttacked(LivingEntity user, Entity attacker, float damage, int level);

}
