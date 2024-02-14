package com.oblivioussp.spartanshields.event;

import com.oblivioussp.spartanshields.data.ModItemModelProvider;
import com.oblivioussp.spartanshields.data.ModItemTagsProvider;
import com.oblivioussp.spartanshields.data.ModRecipeProvider;
import com.oblivioussp.spartanshields.data.ModSoundDefinitionsProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler 
{

	@SubscribeEvent
	public static void onDataGather(GatherDataEvent ev)
	{
		DataGenerator gen = ev.getGenerator();
		gen.addProvider(ev.includeServer(), new ModItemTagsProvider(gen, ev.getExistingFileHelper()));
		gen.addProvider(ev.includeServer(), new ModRecipeProvider(gen));
		gen.addProvider(true, new ModItemModelProvider(gen, ev.getExistingFileHelper()));
		gen.addProvider(true, new ModSoundDefinitionsProvider(gen, ev.getExistingFileHelper()));
	}
}
