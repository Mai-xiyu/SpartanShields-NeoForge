package com.oblivioussp.spartanshields.item;

/*import java.util.List;
import java.util.function.Consumer;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.client.render.item.TowerShieldBEWLR;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

public class BasicTowerShieldItem extends BasicShieldItem 
{

	public BasicTowerShieldItem(String unlocName, TierSS toolMaterial, int defaultMaxDamage, Properties prop) 
	{
		super(unlocName, toolMaterial, defaultMaxDamage, prop);
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) 
	{
		consumer.accept(new IItemRenderProperties() {
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() 
			{
				return TowerShieldBEWLR.INSTANCE;
			}
		});
	}
	
	@Override
	public void addEffectsTooltip(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
    	if(!stack.isEmpty() && stack.hasTag() && stack.getTag().contains("BlockEntityTag"))
    	{
    		DyeColor dyeColor = ShieldItem.getColor(stack);
    		tooltip.add(new TextComponent(""));
    		tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + ".has_patterns"));
    		tooltip.add(new TranslatableComponent(String.format("block.minecraft.%s_banner", dyeColor.name().toLowerCase())).withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
    		BannerItem.appendHoverTextFromBannerBlockEntityTag(stack, tooltip);
    	}
		super.addEffectsTooltip(stack, level, tooltip, flagIn);
	}
}*/