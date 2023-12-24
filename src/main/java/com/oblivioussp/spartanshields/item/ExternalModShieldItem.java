package com.oblivioussp.spartanshields.item;

import java.util.List;

import javax.annotation.Nullable;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExternalModShieldItem extends BasicShieldItem
{
	protected String modName;

	public ExternalModShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, String externalModName, Item.Properties prop) 
	{
		super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
		modName = externalModName;
	}

	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
    	if(!Config.INSTANCE.forceDisableUncraftableTooltips.get())
    	{
    		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".uncraftable_missing_mods", I18n.get("mod." + ModSpartanShields.ID + "." + modName)).withStyle(ChatFormatting.RED));
    	}
    	
    	super.appendHoverText(stack, level, tooltip, flagIn);
    }
}
