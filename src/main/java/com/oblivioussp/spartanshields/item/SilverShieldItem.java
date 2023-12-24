package com.oblivioussp.spartanshields.item;

import java.util.List;

import javax.annotation.Nullable;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SilverShieldItem extends BasicShieldItem implements IDamageShield
{
	
	public SilverShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
	}
	
	public SilverShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop) 
	{
		this(toolMaterial, defaultMaxDamage, false, prop);
	}
	
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
    	super.appendHoverText(stack, level, tooltip, flagIn);
    	tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".on_block", new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".shield_silver.desc").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GOLD));
    }
    
    @Override
    public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage)
    {
    	// Damage undead mobs that attack directly
    	if(attacker instanceof LivingEntity)
    	{
    		LivingEntity attackerLiving = (LivingEntity)attacker;
    		
    		if(attackerLiving.getMobType() == MobType.UNDEAD)
    		{
    			attackerLiving.hurt(DamageSource.playerAttack(player), 2.0f);
    		}
    	}
    }
}
