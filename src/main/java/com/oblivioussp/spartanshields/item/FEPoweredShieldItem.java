package com.oblivioussp.spartanshields.item;

import java.util.List;

import javax.annotation.Nullable;

import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.client.ClientHelper;
import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.util.EnergyCapabilityProviderItem;
import com.oblivioussp.spartanshields.util.PowerUnit;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class FEPoweredShieldItem extends ShieldBaseItem implements IDamageShield, IItemPoweredFE
{
	public static final String NBT_ENERGY = "Energy";
	
	protected int energyCapacity;
	protected int maxEnergyReceive;
	protected String modName;
	protected PowerUnit preferredEnergyUnit;

	public FEPoweredShieldItem(int capacity, int maxReceive, String modName, PowerUnit preferredUnit, boolean isTowerShieldIn, Item.Properties prop)
	{
		super(0, isTowerShieldIn, prop);
		this.energyCapacity = isTowerShieldIn ? Mth.floor(capacity * 1.25f) : capacity;
		this.maxEnergyReceive = maxReceive;
		this.modName = modName;
		this.preferredEnergyUnit = preferredUnit;

		if(FMLEnvironment.dist.isClient())
			ClientHelper.registerPoweredShieldPropertyOverrides(this);
	}
	
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> subItems)
    {
    	if(allowdedIn(group))
    	{
	    	ItemStack fullShield = new ItemStack(this);
	    	fullShield.getOrCreateTag().putInt(NBT_ENERGY, energyCapacity);
	    	
	        subItems.add(new ItemStack(this));
	        subItems.add(fullShield);
    	}
    }
	
	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}
	
	@Override
	public int getBarWidth(ItemStack stack)
	{
		int energy = stack.getOrCreateTag().getInt(NBT_ENERGY);
		return Math.round(13.0f * ((float)energy) / (float)energyCapacity);
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack) 
	{
		int energy = stack.getOrCreateTag().getInt(NBT_ENERGY);
        return energy < energyCapacity;
	}
	
	@Override
	public int getBarColor(ItemStack stack) 
	{
    	return 0x69B3FF;
	}
	
/*	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		int energy = stack.getOrCreateTag().getInt("Energy");
		return ((double)energyCapacity - energy) / (double)energyCapacity;
	}*/
	
	/**
     * Returns the packed int RGB value used to render the durability bar in the GUI.
     * Defaults to a value based on the hue scaled as the damage decreases, but can be overriden.
     *
     * @param stack Stack to get durability from
     * @return A packed RGB value for the durability colour (0x00RRGGBB)
     */
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
    	return 0x69B3FF;
    }
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return energyCapacity;
	}
	
/*	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
		int energy = stack.getOrCreateTag().getInt(NBT_ENERGY);
        return energy < energyCapacity;
    }*/
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
		LocalizedNumberFormatter formatter = NumberFormatter.withLocale(Minecraft.getInstance().getLanguageManager().getSelected().getJavaLocale());
    	tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getCapacityTranslationKey(), formatter.format(Mth.floor(this.getFEStored(stack) * preferredEnergyUnit.getEnergyScaleToFE())), formatter.format(Mth.floor(this.getFECapacity(stack)  * preferredEnergyUnit.getEnergyScaleToFE()))));
    	tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getEnergyChargeRateTranslationKey(), formatter.format(Mth.floor(this.maxEnergyReceive * preferredEnergyUnit.getEnergyScaleToFE()))));
    	tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getEnergyPerDamageTranslationKey(), formatter.format(Mth.floor(Config.INSTANCE.damageToFEMultiplier.get() * 2 * preferredEnergyUnit.getEnergyScaleToFE()))));
    	tooltip.add(new TranslatableComponent("tooltip." + ModSpartanShields.ID + "." + "fe_shield.desc"));
//    	this.addShieldBashTooltip(stack, level, tooltip, flagIn);
    }
	
    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
    	if(this.getFEStored(stack) > 0)
    		return UseAnim.BLOCK;
    	else
    		return UseAnim.NONE;
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        player.startUsingItem(hand);
        ItemStack stack = player.getItemInHand(hand);
        if(this.getFEStored(stack) > 0)
        	return InteractionResultHolder.consume(stack);
        else
        {
        	if(level.isClientSide)
        		player.sendMessage(new TextComponent(ChatFormatting.YELLOW.toString()
        			+ I18n.get("message." + ModSpartanShields.ID + ".powered_shield_block_fail", stack.getHoverName().getString())), Util.NIL_UUID);
        	return InteractionResultHolder.fail(stack);
        }
    }

	@Override
	public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) 
	{
		int energyToUse = Mth.floor((float)(damage));
		
		// Remove FE from the shield to absorb the damage.
		int currentEnergy = shieldStack.getOrCreateTag().getInt(NBT_ENERGY);
		int energyRemoved = Math.min(energyToUse, currentEnergy);
		
		currentEnergy -= energyRemoved;
		shieldStack.getTag().putInt(NBT_ENERGY, currentEnergy);
		
		if(currentEnergy == 0)
			player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + player.level.random.nextFloat() * 0.4F);
	}
	
	public FEPoweredShieldItem setCapacity(int capacity) 
	{
		this.energyCapacity = capacity;
		return this;
	}

	public FEPoweredShieldItem  setMaxReceive(int maxReceive) 
	{
		this.maxEnergyReceive = maxReceive;
		return this;
	}

	/* IItemPoweredFE */
	@Override
	public int receiveFE(ItemStack container, int maxReceive, boolean simulate) 
	{
		int energy = container.getOrCreateTag().getInt(NBT_ENERGY);
		int energyReceived = Math.min(energyCapacity - energy, Math.min(this.maxEnergyReceive, maxReceive));

		if (!simulate)
		{
			energy += energyReceived;
			container.getTag().putInt(NBT_ENERGY, energy);
		}
		return energyReceived;
	}

	@Override
	public int extractFE(ItemStack container, int maxExtract, boolean simulate) 
	{
		return 0;
	}

	@Override
	public int getFEStored(ItemStack container) {

		if (!container.getOrCreateTag().contains(NBT_ENERGY)) 
			return 0;
		
		return container.getTag().getInt(NBT_ENERGY);
	}

	@Override
	public int getFECapacity(ItemStack container) 
	{
		return energyCapacity;
	}

	@Override
	public boolean canExtractFE(ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean canReceiveFE(ItemStack stack) 
	{
		return true;
	}
	
	/**
     * Called from ItemStack.setItem, will hold extra data for the life of this ItemStack.
     * Can be retrieved from stack.getCapabilities()
     * The NBT can be null if this is not called from readNBT or if the item the stack is
     * changing FROM is different then this item, or the previous item had no capabilities.
     *
     * This is called BEFORE the stacks item is set so you can use stack.getItem() to see the OLD item.
     * Remember that getItem CAN return null.
     *
     * @param stack The ItemStack
     * @param nbt NBT of this item serialized, or null.
     * @return A holder instance associated with this ItemStack where you can hold capabilities for the life of this item.
     */
	@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
    {
        return new EnergyCapabilityProviderItem(stack, this);
    }
}
