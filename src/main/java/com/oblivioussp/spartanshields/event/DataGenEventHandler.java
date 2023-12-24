package com.oblivioussp.spartanshields.event;

import com.oblivioussp.spartanshields.data.ModItemModelProvider;
import com.oblivioussp.spartanshields.data.ModItemTagsProvider;
import com.oblivioussp.spartanshields.data.ModRecipeProvider;
import com.oblivioussp.spartanshields.data.ModSoundDefinitionsProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler 
{

	@SubscribeEvent
	public static void onDataGather(GatherDataEvent ev)
	{
		DataGenerator gen = ev.getGenerator();
		gen.addProvider(new ModItemTagsProvider(gen, ev.getExistingFileHelper()));
		gen.addProvider(new ModRecipeProvider(gen));
		gen.addProvider(new ModItemModelProvider(gen, ev.getExistingFileHelper()));
		gen.addProvider(new ModSoundDefinitionsProvider(gen, ev.getExistingFileHelper()));
	}
}
