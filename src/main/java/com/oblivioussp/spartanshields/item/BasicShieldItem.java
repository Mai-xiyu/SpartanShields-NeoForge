package com.oblivioussp.spartanshields.item;

import java.util.List;

import javax.annotation.Nullable;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.client.ClientHelper;
import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class BasicShieldItem extends ShieldBaseItem
{
	protected final TierSS material;	// The base material used for this shield
	
	protected boolean doCraftCheck = true;
	protected boolean canBeCrafted = true;

	public BasicShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop)
	{
		super(defaultMaxDamage, isTowerShieldIn, prop);
		material = toolMaterial;
		
		if(FMLEnvironment.dist.isClient())
			ClientHelper.registerShieldPropertyOverrides(this);
	}
	
	public BasicShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop)
	{
		this(toolMaterial, defaultMaxDamage, false, prop);
	}
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn)
    {
		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".protection", this.getMaxDamage(stack)));
		
    	if(doCraftCheck && levelIn != null)
    	{
    		if(!Config.INSTANCE.forceDisableUncraftableTooltips.get())
    		{
    			ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        		if(!tagManager.isKnownTagName(material.getRepairTag()) || tagManager.getTag(material.getRepairTag()).isEmpty())
    	    		canBeCrafted = false;
    		}
	    	doCraftCheck = false;
    	}

    	if(!canBeCrafted)
    	{
    		tooltip.add(new TranslatableComponent(String.format("tooltip.%s.uncraftable_missing_material", ModSpartanShields.ID), material.getRepairTagName()).withStyle(ChatFormatting.RED));
    	}
    	
    	/*if(isTowerShield && !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("BlockEntityTag"))
        {
    		DyeColor dyeColor = ShieldItem.getColor(stack);
    		tooltip.add(new TextComponent(""));
    		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".has_patterns"));
    		tooltip.add(new TranslatableComponent(String.format("block.minecraft.%s_banner", dyeColor.name().toLowerCase())).withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
    		BannerItem.appendHoverTextFromBannerBlockEntityTag(stack, tooltip);
        }
    	
    	addEffectsTooltip(stack, levelIn, tooltip, flagIn);*/
    	super.appendHoverText(stack, levelIn, tooltip, flagIn);

//    	this.addShieldBashTooltip(stack, level, tooltip, flagIn);
    }
	
/*	@Override
	public String getDescriptionId(ItemStack stack) 
	{
		return getOrCreateDescriptionId();
	}*/

	/**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    /*@Override
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }*/

    /**
     * Return whether this item is repairable in an anvil.
     */
    /*@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
    	return material.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
    }*/
	
	@Override
	public int getEnchantmentValue() 
	{
		return material.getEnchantmentValue();
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) 
	{
		return material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}
