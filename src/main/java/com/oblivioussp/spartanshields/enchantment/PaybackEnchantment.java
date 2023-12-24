package com.oblivioussp.spartanshields.enchantment;

import com.oblivioussp.spartanshields.init.ModSounds;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PaybackEnchantment extends EnchantmentSS
{
	public static final String NBT_PAYBACK_DMG = "PaybackDamage";

	public PaybackEnchantment(Rarity rarityIn, EquipmentSlot... slots)
	{
		super(rarityIn, EnchantmentSS.TYPE_SHIELD, slots);
//		this.setRegistryName(ModSpartanShields.ID, "payback");
	}
	
	@Override
	public int getMinCost(int enchantmentLevel) 
	{
		return 15 + (enchantmentLevel - 1) * 7;
	}
	
	@Override
	public int getMaxCost(int enchantmentLevel)
	{
		return super.getMinCost(enchantmentLevel) + 50;
	}
	
	@Override
	public int getMaxLevel()
	{
		return 4;
	}

	@Override
	public void onUserAttacked(LivingEntity user, Entity attacker, float damage, int level) 
	{
		ItemStack activeStack = user.getUseItem();
		if(!activeStack.isEmpty())
		{
			float currentDmg = activeStack.getOrCreateTag().getFloat(NBT_PAYBACK_DMG);
			float maxDmg = getMaxDamageCapacity(level);
			float absorbedDmg = damage * getAbsorbedDamageRatio();
			
			currentDmg = Mth.clamp(currentDmg + absorbedDmg, 0.0f, maxDmg);
			activeStack.getTag().putFloat(NBT_PAYBACK_DMG, currentDmg);
			
			// Let the player know that the shield is at maximum damage capacity
			if(currentDmg == maxDmg)
				user.level.playSound((Player)null, user.getX(), user.getY(), user.getZ(), ModSounds.SHIELD_PAYBACK_CHARGE.get(), user.getSoundSource(), 0.5f, 2.0f);
		}
	}
	
	protected float getMaxDamageCapacity(int level)
	{
		return 2.0f * level;
	}
	
	protected float getAbsorbedDamageRatio()
	{
		return 0.5f;
	}

}
