package com.oblivioussp.spartanshields.network;

import java.util.function.Supplier;

import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.enchantment.PaybackEnchantment;
import com.oblivioussp.spartanshields.init.ModEnchantments;
import com.oblivioussp.spartanshields.init.ModSounds;
import com.oblivioussp.spartanshields.init.ModStats;
import com.oblivioussp.spartanshields.tags.ModItemTags;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.network.NetworkEvent;

public class ShieldBashPacket
{
	protected InteractionHand hand;
	protected int entityId;
	protected boolean attackEntity = false;
	
	public ShieldBashPacket(InteractionHand handIn, int entityIdIn, boolean attackEntityIn)
	{
		hand = handIn;
		entityId = entityIdIn;
		attackEntity = attackEntityIn;
	}
	
	public static void encode(ShieldBashPacket packet, FriendlyByteBuf buf)
	{
		buf.writeEnum(packet.hand);
		buf.writeInt(packet.entityId);
		buf.writeBoolean(packet.attackEntity);
	}
	
	public static ShieldBashPacket decode(FriendlyByteBuf buf)
	{
		return new ShieldBashPacket(buf.readEnum(InteractionHand.class), buf.readInt(), buf.readBoolean());
	}
	
	public static class Handler
	{
		public static void handle(final ShieldBashPacket packet, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> 
			{
				ServerPlayer player = ctx.get().getSender();
				Entity victim = player.level.getEntity(packet.entityId);
				
				if(player.isBlocking())
				{
					ItemStack shieldStack = player.getItemInHand(packet.hand);
					boolean isTowerShield = shieldStack.is(ModItemTags.TOWER_SHIELDS);
					
					if(!shieldStack.isEmpty() && !player.getCooldowns().isOnCooldown(shieldStack.getItem()) && 
							player.getUseItem().is(ModItemTags.SHIELDS_WITH_BASH) && player.getUseItem().getItem().canPerformAction(player.getUseItem(), ToolActions.SHIELD_BLOCK))
					{
						if(packet.attackEntity && victim != null && victim instanceof LivingEntity)
						{
							// Deal minimal damage and knock back foes
							int knockLvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, shieldStack);
							
							// Decide between increased knockback or bashing multiple foes similar to sweeping with a sword (currently implemented)
							//if(isTowerShield)
							//	knockLvl++;
							if(isTowerShield)
							{
								double reach = player.getAttackRange();
								for(LivingEntity entity : player.level.getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(1.0d, 0.25d, 1.0d), 
										(target) -> target != player && target != victim && !target.isAlliedTo(player) && (!(target instanceof ArmorStand) || !((ArmorStand)target).isMarker()) && player.distanceToSqr(target) < reach * reach))
								{
									bashEntity(entity, player, shieldStack, knockLvl, packet.hand);
								}
							}

							boolean powerfulBash = bashEntity(victim, player, shieldStack, knockLvl, packet.hand);
							// Increase the pitch whenever the bash damage is higher
							player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), ModSounds.SHIELD_BASH_HIT.get(), player.getSoundSource(), 1.0F, powerfulBash ? 2.0f : 1.0f);
							player.crit(victim);
							
							// Add to shield bash hits stat
							player.awardStat(ModStats.SHIELD_BASH_HITS);
						}
						else
						{
							// ...swing and a miss...
							player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), ModSounds.SHIELD_BASH_MISS.get(), player.getSoundSource(), 0.5f, 0.01f);
						}
						player.stopUsingItem();
						//player.swing(packet.hand);
						player.getCooldowns().addCooldown(shieldStack.getItem(), isTowerShield ? Config.INSTANCE.cooldownTowerShieldBash.get() : Config.INSTANCE.cooldownShieldBash.get());
//						player.awardStat(Stats.ITEM_USED.get(shieldStack.getItem()));
					}
				}
			});
		}
		
		protected static boolean bashEntity(Entity targetEntity, Player player, ItemStack shieldStack, int knockbackLevel, InteractionHand hand)
		{
			targetEntity.invulnerableTime = 0;
			((LivingEntity)targetEntity).knockback(1.0f + (knockbackLevel), (double)Mth.sin(player.getYRot() * 0.017453292F), (double)(-Mth.cos(player.getYRot() * 0.017453292F)));
			
			float bashDamage = 1.0f;
			// Apply the Payback damage bonus if necessary
			if(EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.PAYBACK.get(), shieldStack) != 0)
			{
				bashDamage += shieldStack.getOrCreateTag().getFloat(PaybackEnchantment.NBT_PAYBACK_DMG);
				shieldStack.getTag().putFloat(PaybackEnchantment.NBT_PAYBACK_DMG, 0.0f);
			}
			
			// Finally deal damage.
			targetEntity.hurt(DamageSource.playerAttack(player), bashDamage);
			shieldStack.hurtAndBreak(5, player, (entity) -> entity.broadcastBreakEvent(hand));
			
			// Set foes on fire when hit with the Shield Bash
			int firebrandLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FIREBRAND.get(), shieldStack);
			if(firebrandLvl != 0)
				targetEntity.setSecondsOnFire(firebrandLvl * 5);
			
			return bashDamage > 1.0f;
		}
	}
}