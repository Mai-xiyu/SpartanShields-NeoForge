package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.client.ClientHelper;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraft.core.registries.BuiltInRegistries;

public class BasicShieldItem extends ShieldBaseItem {
    protected final TierSS material;    // The base material used for this shield

    protected boolean doCraftCheck = true;
    protected boolean canBeCrafted = true;

    public BasicShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop) {
        super(defaultMaxDamage, isTowerShieldIn, prop);
        this.material = toolMaterial;

        if (FMLEnvironment.dist.isClient())
            ClientHelper.registerShieldPropertyOverrides(this);
    }

    public BasicShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop) {
        this(toolMaterial, defaultMaxDamage, false, prop);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".protection", this.getMaxDamage(stack)));

        // Forge TooltipContext doesn't have level() - skip craft check that needs level
        if (this.doCraftCheck) {
            if (!Config.INSTANCE.forceDisableUncraftableTooltips.get()) {
                Optional<HolderSet.Named<net.minecraft.world.item.Item>> tagOpt = BuiltInRegistries.ITEM.getTag(this.material.getRepairTag());
                if (tagOpt.isEmpty() || tagOpt.get().size() == 0)
                    this.canBeCrafted = false;
            }
            this.doCraftCheck = false;
        }

        if (!this.canBeCrafted) {
            tooltip.add(Component.translatable(String.format("tooltip.%s.uncraftable_missing_material", ModSpartanShields.ID), this.material.getRepairTagName()).withStyle(ChatFormatting.RED));
        }
    	
    	/*if(isTowerShield && !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("BlockEntityTag"))
        {
    		DyeColor dyeColor = ShieldItem.getColor(stack);
    		tooltip.add(new TextComponent(""));
    		tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".has_patterns"));
    		tooltip.add(Component.translatable(String.format("block.minecraft.%s_banner", dyeColor.name().toLowerCase())).withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
    		BannerItem.appendHoverTextFromBannerBlockEntityTag(stack, tooltip);
        }
    	
    	addEffectsTooltip(stack, levelIn, tooltip, flagIn);*/
        super.appendHoverText(stack, context, tooltip, flagIn);

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
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return this.material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
