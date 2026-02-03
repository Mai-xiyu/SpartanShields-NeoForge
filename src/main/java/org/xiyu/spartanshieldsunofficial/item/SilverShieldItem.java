package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import javax.annotation.Nullable;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn)
    {
    	super.appendHoverText(stack, context, tooltip, flagIn);
    	tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".on_block", Component.translatable("tooltip." + ModSpartanShields.ID + ".shield_silver.desc").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GOLD));
    }
    
    @Override
    public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage)
    {
    	// Damage undead mobs that attack directly
    	if(attacker instanceof LivingEntity)
    	{
    		LivingEntity attackerLiving = (LivingEntity)attacker;
    		
			if(attackerLiving.getType().is(EntityTypeTags.UNDEAD))
    		{
    			attackerLiving.hurt(player.damageSources().playerAttack(player), 2.0f);
    		}
    	}
    }
}
