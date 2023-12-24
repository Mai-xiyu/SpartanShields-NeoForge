package com.oblivioussp.spartanshields;

import com.oblivioussp.spartanshields.client.ClientHelper;
import com.oblivioussp.spartanshields.client.ModKeyBinds;
import com.oblivioussp.spartanshields.config.Config;
import com.oblivioussp.spartanshields.init.ModEnchantments;
import com.oblivioussp.spartanshields.init.ModItems;
import com.oblivioussp.spartanshields.init.ModRecipes;
import com.oblivioussp.spartanshields.init.ModSounds;
import com.oblivioussp.spartanshields.init.ModStats;
import com.oblivioussp.spartanshields.network.NetworkHandler;
import com.oblivioussp.spartanshields.util.Log;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModSpartanShields.ID)
public class ModSpartanShields
{
    public static final String ID = "spartanshields";
    public static final String NAME = "Spartan Shields";
    
    public ModSpartanShields()
    {
        Log.info("Constructing Mod: " + NAME);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onSetup);
        modBus.addListener(this::onClientSetup);
        
        ModItems.REGISTER.register(modBus);
        ModEnchantments.REGISTER.register(modBus);
        ModRecipes.REGISTER.register(modBus);
        ModRecipes.initConditions();
        ModSounds.REGISTER.register(modBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        
    }

    private void onSetup(FMLCommonSetupEvent ev)
    {
        Log.info("Setting up " + NAME + "!");
        ev.enqueueWork(() ->
        {
        	NetworkHandler.init();
        	ModStats.register();
        });
    }

    private void onClientSetup(FMLClientSetupEvent ev)
    {
        Log.info("Setting up Client for " + NAME + "!");
        ClientHelper.registerItemColors();
        ModKeyBinds.registerKeyBinds();
    }
}
