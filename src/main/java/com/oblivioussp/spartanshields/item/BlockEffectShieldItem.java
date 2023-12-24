package com.oblivioussp.spartanshields.item;

import java.util.List;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BlockEffectShieldItem extends BasicShieldItem implements IDamageShield 
{
	private final MobEffect effect;
	private final int effectTicks;
	private final int effectLevel;
	
	public BlockEffectShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, MobEffect effectIn, int effectTicksIn, int effectLevelIn, Properties prop)
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
		effect = effectIn;
		effectTicks = effectTicksIn;
		effectLevel = effectLevelIn;
	}
	
	public BlockEffectShieldItem(TierSS toolMaterial, int defaultMaxDamage, MobEffect effectIn, int effectTicksIn, int effectLevelIn, Properties prop)
	{
		this(toolMaterial, defaultMaxDamage, false, effectIn, effectTicksIn, effectLevelIn, prop);
	}

	@Override
	public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) 
	{
    	// Damage mobs that attack directly
    	if(attacker instanceof LivingEntity)
    	{
    		LivingEntity attackerLiving = (LivingEntity)attacker;
    		attackerLiving.addEffect(new MobEffectInstance(effect, effectTicks, effectLevel, false, true));
    	}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level levelIn, List<Component> tooltip, TooltipFlag flagIn) 
	{
		super.appendHoverText(stack, levelIn, tooltip, flagIn);
		TranslatableComponent effectName = new TranslatableComponent(effect.getDescriptionId());
		if(effectLevel > 0)
			effectName = new TranslatableComponent("potion.withAmplifier", effectName, new TranslatableComponent("potion.potency." + effectLevel));
		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".on_block", new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".inflict_mob_effect.desc", effectName.withStyle(ChatFormatting.AQUA), effectTicks / 20.0f).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GOLD));
	}

}
