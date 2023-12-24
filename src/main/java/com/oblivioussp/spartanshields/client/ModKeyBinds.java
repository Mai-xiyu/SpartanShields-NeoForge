package com.oblivioussp.spartanshields.client;

import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class ModKeyBinds 
{
	public static final KeyMapping KEY_ALT_SHIELD_BASH = new KeyMapping("key." + ModSpartanShields.ID + ".alt_shield_bash", -1, "key." + ModSpartanShields.ID + ".category");
	
	public static void registerKeyBinds()
	{
		ClientRegistry.registerKeyBinding(KEY_ALT_SHIELD_BASH);
	}
}
