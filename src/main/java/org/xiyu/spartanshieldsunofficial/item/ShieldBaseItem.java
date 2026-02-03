package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.client.render.item.TowerShieldBEWLR;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModEnchantments;
import org.xiyu.spartanshieldsunofficial.util.EnchantmentConstants;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

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
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions() {
			
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() 
			{
				return isTowerShield ? TowerShieldBEWLR.INSTANCE : IClientItemExtensions.super.getCustomRenderer() ;
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
    public void appendHoverText(ItemStack stackIn, Item.TooltipContext context, List<Component> tooltipIn, TooltipFlag flagIn) 
    {
    	if(isTowerShield && !stackIn.isEmpty())
        {
    		BannerPatternLayers patterns = stackIn.get(DataComponents.BANNER_PATTERNS);
    		if(patterns != null && !patterns.layers().isEmpty())
    		{
    			DyeColor dyeColor = stackIn.get(DataComponents.BASE_COLOR);
    			if(dyeColor == null) dyeColor = DyeColor.WHITE;
    			tooltipIn.add(Component.empty());
    			tooltipIn.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".has_patterns"));
    			tooltipIn.add(Component.translatable(String.format("block.minecraft.%s_banner", dyeColor.name().toLowerCase())).withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
    			BannerItem.appendHoverTextFromBannerBlockEntityTag(stackIn, tooltipIn);
    		}
        }
    	
    	addEffectsTooltip(stackIn, context, tooltipIn, flagIn);
    }
	
	@Override
	public String getDescriptionId(ItemStack stack) 
	{
		// Prevents the display name from being overwritten by the shield base class (colour is now relegated to the tooltip) 
		return getOrCreateDescriptionId();
	}
    
    @OnlyIn(Dist.CLIENT)
    public void addEffectsTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) 
    {
    	// Get payback enchantment level - check if registry access is available
    	Level level = context.level();
    	if(level != null)
    	{
    		int paybackLevel = EnchantmentConstants.getEnchantmentLevel(level, stack, ModEnchantments.PAYBACK);
    		if(paybackLevel != 0)
    		{
    			float paybackDamage = stack.getOrDefault(ModDataComponents.PAYBACK_DAMAGE.get(), 0.0f);
    			tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".payback_bonus", ChatFormatting.GRAY.toString() + Float.toString(paybackDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
    		}
    	}
    }
}
