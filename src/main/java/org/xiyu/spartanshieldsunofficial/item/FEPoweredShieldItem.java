package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import javax.annotation.Nullable;

import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.client.ClientHelper;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.util.PowerUnit;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;

public class FEPoweredShieldItem extends ShieldBaseItem implements IDamageShield, IItemPoweredFE
{
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
	
	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}
	
	@Override
	public int getBarWidth(ItemStack stack)
	{
		int energy = getStoredEnergy(stack);
		return Math.round(13.0f * ((float)energy) / (float)energyCapacity);
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack) 
	{
		int energy = getStoredEnergy(stack);
        return energy < energyCapacity;
	}
	
	@Override
	public int getBarColor(ItemStack stack) 
	{
    	return 0x69B3FF;
	}
	
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
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn)
    {
		LocalizedNumberFormatter formatter = NumberFormatter.withLocale(Minecraft.getInstance().getLanguageManager().getJavaLocale());
    	tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getCapacityTranslationKey(), formatter.format(Mth.floor(this.getFEStored(stack) * preferredEnergyUnit.getEnergyScaleToFE())).toString(), formatter.format(Mth.floor(this.getFECapacity(stack)  * preferredEnergyUnit.getEnergyScaleToFE())).toString()));
    	tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getEnergyChargeRateTranslationKey(), formatter.format(Mth.floor(this.maxEnergyReceive * preferredEnergyUnit.getEnergyScaleToFE())).toString()));
    	tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + preferredEnergyUnit.getEnergyPerDamageTranslationKey(), formatter.format(Mth.floor(Config.INSTANCE.damageToFEMultiplier.get() * 2 * preferredEnergyUnit.getEnergyScaleToFE())).toString()));
    	tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + "." + "fe_shield.desc"));
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
        		player.sendSystemMessage(Component.literal(ChatFormatting.YELLOW.toString()
        			+ I18n.get("message." + ModSpartanShields.ID + ".powered_shield_block_fail", stack.getHoverName().getString())));
        	return InteractionResultHolder.fail(stack);
        }
    }

	@Override
	public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) 
	{
		int energyToUse = Mth.floor((float)(damage));
		
		// Remove FE from the shield to absorb the damage.
		int currentEnergy = getStoredEnergy(shieldStack);
		int energyRemoved = Math.min(energyToUse, currentEnergy);
		
		currentEnergy -= energyRemoved;
		setStoredEnergy(shieldStack, currentEnergy);
		
		if(currentEnergy == 0)
		{
			Level level = player.level();
			player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
		}
	}
	
	public FEPoweredShieldItem setCapacity(int capacity) 
	{
		this.energyCapacity = capacity;
		return this;
	}

	public FEPoweredShieldItem setMaxReceive(int maxReceive) 
	{
		this.maxEnergyReceive = maxReceive;
		return this;
	}

	/* IItemPoweredFE */
	@Override
	public int receiveFE(ItemStack container, int maxReceive, boolean simulate) 
	{
		int energy = getStoredEnergy(container);
		int energyReceived = Math.min(energyCapacity - energy, Math.min(this.maxEnergyReceive, maxReceive));

		if (!simulate)
		{
			energy += energyReceived;
			setStoredEnergy(container, energy);
		}
		return energyReceived;
	}

	@Override
	public int extractFE(ItemStack container, int maxExtract, boolean simulate) 
	{
		return 0;
	}

	@Override
	public int getFEStored(ItemStack container)
	{
		return container.getOrDefault(ModDataComponents.STORED_ENERGY.get(), 0);
	}
	
	/**
	 * Gets the stored energy from an ItemStack
	 * @param stack the stack to get energy from
	 * @return the stored energy amount
	 */
	public int getStoredEnergy(ItemStack stack)
	{
		return getFEStored(stack);
	}
	
	/**
	 * Sets the stored energy on an ItemStack
	 * @param stack the stack to set energy on
	 * @param energy the amount of energy to set
	 */
	public void setStoredEnergy(ItemStack stack, int energy)
	{
		stack.set(ModDataComponents.STORED_ENERGY.get(), energy);
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
}
