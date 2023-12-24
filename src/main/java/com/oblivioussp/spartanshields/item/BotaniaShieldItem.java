package com.oblivioussp.spartanshields.item;

import java.util.function.Consumer;

import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class BotaniaShieldItem extends BasicShieldItem
{
	protected int manaPerDamage = 60;
	
	public BotaniaShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, int manaPerDamage, Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
		this.manaPerDamage = manaPerDamage;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(!level.isClientSide && entityIn instanceof Player && 
				stack.getDamageValue() > 0 && 
				ManaItemHandler.instance().requestManaExactForTool(stack, (Player)entityIn, manaPerDamage * 2, true))
		{
			stack.setDamageValue(stack.getDamageValue() - 1);
		}
	}
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken)
	{
		return ToolCommons.damageItemIfPossible(stack, amount, entity, manaPerDamage);
//		return super.damageItem(stack, amount, entity, onBroken);
	}
}
