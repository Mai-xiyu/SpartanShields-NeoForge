package com.oblivioussp.spartanshields.event;

import java.util.Map;

import com.oblivioussp.spartanshields.enchantment.EnchantmentSS;
import com.oblivioussp.spartanshields.item.IDamageShield;
import com.oblivioussp.spartanshields.item.ShieldBaseItem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler 
{
//	protected static int tickCounter = 0;
	
	// Attack Event - Handles damage and enchantment triggering for shields.
	@SubscribeEvent
	public static void attackEvent(LivingAttackEvent ev)
	{
		float damage = ev.getAmount();
		
		if(ev.getEntityLiving() instanceof Player)
		{
			Player player = (Player)ev.getEntityLiving();
			Entity directEntity = ev.getSource().getDirectEntity();
			
			// Potential fix for Spikes damage bug with non-entity damage sources; check for entity causing damage
			if(!player.getUseItem().isEmpty() && player.getUseItem().getItem() instanceof ShieldBaseItem && ev.getSource().getDirectEntity() != null)
			{
				ItemStack activeStack = player.getUseItem();
				
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(activeStack);
				for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet())
				{
					if(enchantment.getKey() instanceof EnchantmentSS)
					{
						((EnchantmentSS)enchantment.getKey()).onUserAttacked(player, ev.getSource().getEntity(), ev.getAmount(), enchantment.getValue());
					}
				}
				
				// Copy of Player.damageShield() (Allowing for custom shields to take damage)
				// Unsure if it's the same method name due to name changes since early 1.16.x
				if (damage >= 3.0F && !activeStack.isEmpty() && activeStack.getItem() instanceof IDamageShield && directEntity instanceof LivingEntity)
		        {
					((IDamageShield)activeStack.getItem()).damageShield(activeStack, player, (LivingEntity)directEntity, damage);
		        }
			}
		}
	}
}
