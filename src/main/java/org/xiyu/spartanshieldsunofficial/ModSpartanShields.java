package org.xiyu.spartanshieldsunofficial;

import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModCreativeTabs;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.init.ModRecipes;
import org.xiyu.spartanshieldsunofficial.init.ModSounds;
import org.xiyu.spartanshieldsunofficial.init.ModStats;
import org.xiyu.spartanshieldsunofficial.util.Log;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ModSpartanShields.ID)
public class ModSpartanShields
{
    public static final String ID = "spartan_shields_unofficial";
    public static final String NAME = "Spartan Shields Unofficial";
    
    public ModSpartanShields(IEventBus modBus, ModContainer modContainer)
    {
        Log.info("Constructing Mod: " + NAME);

        modBus.addListener(this::onSetup);
        modBus.addListener(this::onClientSetup);
        
        ModDataComponents.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
        ModCreativeTabs.REGISTER.register(modBus);
        // Note: Enchantments are now data-driven in 1.21, no longer registered via code
        ModRecipes.REGISTER.register(modBus);
        ModRecipes.CONDITION_CODECS.register(modBus);
        ModSounds.REGISTER.register(modBus);
        ModStats.REGISTER.register(modBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }

    private void onSetup(FMLCommonSetupEvent ev)
    {
        Log.info("Setting up " + NAME + "!");
        ev.enqueueWork(() ->
        {
        	ModStats.init();
        });
    }

    private void onClientSetup(FMLClientSetupEvent ev)
    {
        Log.info("Setting up Client for " + NAME + "!");
    }
}
