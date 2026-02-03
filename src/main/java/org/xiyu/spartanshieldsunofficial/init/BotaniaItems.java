package org.xiyu.spartanshieldsunofficial.init;

import java.util.function.Supplier;

import org.xiyu.spartanshieldsunofficial.item.BotaniaShieldItem;
import org.xiyu.spartanshieldsunofficial.item.ElementiumShieldItem;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.util.Defaults;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.world.item.Item;

public class BotaniaItems 
{
	public static Supplier<ShieldBaseItem> createManasteelBasicShield()
	{
		return () -> new BotaniaShieldItem(TierSS.MANASTEEL, Defaults.DefaultDurabilityManasteelShield, false, 60, new Item.Properties());
	}
	
	public static Supplier<ShieldBaseItem> createTerrasteelBasicShield()
	{
		return () -> new BotaniaShieldItem(TierSS.TERRASTEEL, Defaults.DefaultDurabilityTerrasteelShield, false, 100, new Item.Properties());
	}
	
	public static Supplier<ShieldBaseItem> createElementiumBasicShield()
	{
		return () -> new ElementiumShieldItem(TierSS.ELEMENTIUM, Defaults.DefaultDurabilityElementiumShield, false, 60, new Item.Properties());
	}
	
	public static Supplier<ShieldBaseItem> createManasteelTowerShield()
	{
		return () -> new BotaniaShieldItem(TierSS.MANASTEEL, Defaults.DefaultDurabilityManasteelShield, true, 60, new Item.Properties());
	}
	
	public static Supplier<ShieldBaseItem> createTerrasteelTowerShield()
	{
		return () -> new BotaniaShieldItem(TierSS.TERRASTEEL, Defaults.DefaultDurabilityTerrasteelShield, true, 100, new Item.Properties());
	}
	
	public static Supplier<ShieldBaseItem> createElementiumTowerShield()
	{
		return () -> new ElementiumShieldItem(TierSS.ELEMENTIUM, Defaults.DefaultDurabilityElementiumShield, true, 60, new Item.Properties());
	}
}
