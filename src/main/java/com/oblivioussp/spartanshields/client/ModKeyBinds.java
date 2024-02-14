package com.oblivioussp.spartanshields.client;

import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyBinds 
{
	public static final KeyMapping KEY_ALT_SHIELD_BASH = new KeyMapping("key." + ModSpartanShields.ID + ".alt_shield_bash", -1, "key." + ModSpartanShields.ID + ".category");

	@SubscribeEvent
	public static void registerKeyBinds(RegisterKeyMappingsEvent ev)
	{
		ev.register(KEY_ALT_SHIELD_BASH);
	}
}
