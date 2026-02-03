package org.xiyu.spartanshieldsunofficial.item;

import java.util.function.Consumer;

import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
		// Botania API 不可用时，保留基础护盾逻辑
		super.inventoryTick(stack, level, entityIn, itemSlot, isSelected);
	}
}
