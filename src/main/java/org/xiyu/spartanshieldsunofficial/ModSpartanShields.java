package org.xiyu.spartanshieldsunofficial;

import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.init.ModCreativeTabs;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.init.ModRecipes;
import org.xiyu.spartanshieldsunofficial.init.ModSounds;
import org.xiyu.spartanshieldsunofficial.init.ModStats;
import org.xiyu.spartanshieldsunofficial.util.Log;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModSpartanShields.ID)
public class ModSpartanShields {
    public static final String ID = "spartan_shields_unofficial";
    public static final String NAME = "Spartan Shields Unofficial";

    public ModSpartanShields() {
        Log.info("Constructing Mod: " + NAME);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onSetup);
        modBus.addListener(this::onClientSetup);

        // Note: ModDataComponents no longer uses DeferredRegister (NBT-based now)
        ModItems.REGISTER.register(modBus);
        ModCreativeTabs.REGISTER.register(modBus);
        // Note: Enchantments are now data-driven in 1.21, no longer registered via code
        ModRecipes.REGISTER.register(modBus);
        ModRecipes.CONDITION_CODECS.register(modBus);
        ModSounds.REGISTER.register(modBus);
        ModStats.REGISTER.register(modBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }

    private void onSetup(FMLCommonSetupEvent ev) {
        Log.info("Setting up " + NAME + "!");
        ev.enqueueWork(ModStats::init);
    }

    private void onClientSetup(FMLClientSetupEvent ev) {
        Log.info("Setting up Client for " + NAME + "!");
    }
}
