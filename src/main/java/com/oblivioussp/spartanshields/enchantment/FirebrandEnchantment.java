package com.oblivioussp.spartanshields.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class FirebrandEnchantment extends Enchantment
{

	public FirebrandEnchantment(Rarity rarityIn, EquipmentSlot... slots) 
	{
		super(rarityIn, EnchantmentSS.TYPE_SHIELD, slots);
//		this.setRegistryName(ModSpartanShields.ID, "firebrand");
	}
	
    @Override
    public int getMinCost(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }
    
    @Override
    public int getMaxCost(int enchantmentLevel)
    {
        return super.getMinCost(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 2;
    }

/*	@Override
	public void onUserAttacked(LivingEntity user, Entity attacker, int level) 
	{
        ItemStack activeStack = user.getActiveItemStack();
        if(activeStack.isEmpty() || attacker == null)	return;
        
        attacker.setFire(level == 2 ? 10 : 5);
	}*/

}
