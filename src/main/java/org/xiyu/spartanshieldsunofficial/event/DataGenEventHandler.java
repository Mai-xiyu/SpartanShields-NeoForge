package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.data.ModItemModelProvider;
import org.xiyu.spartanshieldsunofficial.data.ModItemTagsProvider;
import org.xiyu.spartanshieldsunofficial.data.ModRecipeProvider;
import org.xiyu.spartanshieldsunofficial.data.ModSoundDefinitionsProvider;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = org.xiyu.spartanshieldsunofficial.ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler {

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent ev) {
        DataGenerator gen = ev.getGenerator();
        PackOutput output = gen.getPackOutput();
        gen.addProvider(ev.includeServer(), new ModItemTagsProvider(output, ev.getLookupProvider(), ev.getExistingFileHelper()));
        gen.addProvider(ev.includeServer(), new ModRecipeProvider(output, ev.getLookupProvider()));
        gen.addProvider(true, new ModItemModelProvider(output, ev.getExistingFileHelper()));
        gen.addProvider(true, new ModSoundDefinitionsProvider(output, ev.getExistingFileHelper()));
    }
}
