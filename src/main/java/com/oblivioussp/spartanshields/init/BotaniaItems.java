package com.oblivioussp.spartanshields.init;

import java.util.function.Supplier;

import com.oblivioussp.spartanshields.item.BotaniaShieldItem;
import com.oblivioussp.spartanshields.item.ElementiumShieldItem;
import com.oblivioussp.spartanshields.item.ShieldBaseItem;
import com.oblivioussp.spartanshields.util.Defaults;
import com.oblivioussp.spartanshields.util.TierSS;

import net.minecraft.world.item.Item;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.lib.ModTags;

public class BotaniaItems 
{
	private static TierSS TIER_MANASTEEL = new TierSS(BotaniaAPI.instance().getManasteelItemTier(), ModTags.Items.INGOTS_MANASTEEL);
	private static TierSS TIER_TERRASTEEL = new TierSS(BotaniaAPI.instance().getTerrasteelItemTier(), ModTags.Items.INGOTS_TERRASTEEL);
	private static TierSS TIER_ELEMENTIUM = new TierSS(BotaniaAPI.instance().getElementiumItemTier(), ModTags.Items.INGOTS_ELEMENTIUM);
	
	public static Supplier<ShieldBaseItem> createManasteelBasicShield()
	{
		return () -> new BotaniaShieldItem(TIER_MANASTEEL, Defaults.DefaultDurabilityManasteelShield, false, 60, new Item.Properties().tab(ModItems.tabSS));
	}
	
	public static Supplier<ShieldBaseItem> createTerrasteelBasicShield()
	{
		return () -> new BotaniaShieldItem(TIER_TERRASTEEL, Defaults.DefaultDurabilityTerrasteelShield, false, 100, new Item.Properties().tab(ModItems.tabSS));
	}
	
	public static Supplier<ShieldBaseItem> createElementiumBasicShield()
	{
		return () -> new ElementiumShieldItem(TIER_ELEMENTIUM, Defaults.DefaultDurabilityElementiumShield, false, 60, new Item.Properties().tab(ModItems.tabSS));
	}
	
	public static Supplier<ShieldBaseItem> createManasteelTowerShield()
	{
		return () -> new BotaniaShieldItem(TIER_MANASTEEL, Defaults.DefaultDurabilityManasteelShield, true, 60, new Item.Properties().tab(ModItems.tabSS));
	}
	
	public static Supplier<ShieldBaseItem> createTerrasteelTowerShield()
	{
		return () -> new BotaniaShieldItem(TIER_TERRASTEEL, Defaults.DefaultDurabilityTerrasteelShield, true, 100, new Item.Properties().tab(ModItems.tabSS));
	}
	
	public static Supplier<ShieldBaseItem> createElementiumTowerShield()
	{
		return () -> new ElementiumShieldItem(TIER_ELEMENTIUM, Defaults.DefaultDurabilityElementiumShield, true, 60, new Item.Properties().tab(ModItems.tabSS));
	}
}
