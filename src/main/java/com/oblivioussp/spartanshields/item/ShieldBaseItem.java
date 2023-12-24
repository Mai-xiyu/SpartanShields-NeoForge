package com.oblivioussp.spartanshields.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.client.render.item.TowerShieldBEWLR;
import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.enchantment.PaybackEnchantment;
import com.oblivioussp.spartanshields.init.ModEnchantments;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

public class ShieldBaseItem extends ShieldItem
{
	protected final boolean isTowerShield;
	
    protected int maxDurability;

	public ShieldBaseItem(int defaultDurability, boolean isTowerShieldIn, Item.Properties prop)
	{
		// Increase durability by 25% with tower shields (will be reloaded when the server config is updated)
		super(prop.durability(isTowerShieldIn ? Mth.floor(defaultDurability * 1.25f) :defaultDurability));
		maxDurability = defaultDurability;
		isTowerShield = isTowerShieldIn;
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) 
	{
		consumer.accept(new IItemRenderProperties() {
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() 
			{
				return isTowerShield ? TowerShieldBEWLR.INSTANCE : IItemRenderProperties.super.getItemStackRenderer() ;
			}
		});
	}
    
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
    {
    	return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return maxDurability;
    }

    public void setMaxDamage(int maxDamage)
    {
        maxDurability = isTowerShield ? Mth.floor(maxDamage * Config.INSTANCE.towerShieldDurabilityMultiplier.get()) : maxDamage;
    }
    
    @Override
    public void appendHoverText(ItemStack stackIn, Level levelIn, List<Component> tooltipIn, TooltipFlag flagIn) 
    {
    	if(isTowerShield && !stackIn.isEmpty() && stackIn.hasTag() && stackIn.getTag().contains("BlockEntityTag"))
        {
    		DyeColor dyeColor = ShieldItem.getColor(stackIn);
    		tooltipIn.add(new TextComponent(""));
    		tooltipIn.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".has_patterns"));
    		tooltipIn.add(new TranslatableComponent(String.format("block.minecraft.%s_banner", dyeColor.name().toLowerCase())).withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
    		BannerItem.appendHoverTextFromBannerBlockEntityTag(stackIn, tooltipIn);
        }
    	
    	addEffectsTooltip(stackIn, levelIn, tooltipIn, flagIn);
    }
	
	@Override
	public String getDescriptionId(ItemStack stack) 
	{
		// Prevents the display name from being overwritten by the shield base class (colour is now relegated to the tooltip) 
		return getOrCreateDescriptionId();
	}
    
    @OnlyIn(Dist.CLIENT)
    public void addEffectsTooltip(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) 
    {
    	if(EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.PAYBACK.get(), stack) != 0)
    	{
    		float paybackDamage = stack.getOrCreateTag().getFloat(PaybackEnchantment.NBT_PAYBACK_DMG);
    		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".payback_bonus", ChatFormatting.GRAY.toString() + Float.toString(paybackDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
    	}
    }
}
