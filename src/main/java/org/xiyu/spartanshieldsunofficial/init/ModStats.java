package org.xiyu.spartanshieldsunofficial.init;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStats {
    public static final DeferredRegister<ResourceLocation> REGISTER = DeferredRegister.create(Registries.CUSTOM_STAT, ModSpartanShields.ID);

    public static final Supplier<ResourceLocation> SHIELD_BASH_HITS = REGISTER.register("shield_bash_hits",
            () -> ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, "shield_bash_hits"));

    public static void init() {
        // Called after registration to initialize the stat types
        Stats.CUSTOM.get(SHIELD_BASH_HITS.get());
    }
}
