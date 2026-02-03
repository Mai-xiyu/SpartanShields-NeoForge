package org.xiyu.spartanshieldsunofficial.client;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyBinds 
{
	public static final KeyMapping KEY_ALT_SHIELD_BASH = new KeyMapping("key." + ModSpartanShields.ID + ".alt_shield_bash", -1, "key." + ModSpartanShields.ID + ".category");

	@SubscribeEvent
	public static void registerKeyBinds(RegisterKeyMappingsEvent ev)
	{
		ev.register(KEY_ALT_SHIELD_BASH);
	}
}
