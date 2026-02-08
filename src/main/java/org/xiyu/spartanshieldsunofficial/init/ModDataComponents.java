package org.xiyu.spartanshieldsunofficial.init;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom data components for shields
 */
public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTER = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ModSpartanShields.ID);

    // Payback enchantment stored damage
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> PAYBACK_DAMAGE =
            REGISTER.registerComponentType("payback_damage", builder -> builder
                    .persistent(Codec.FLOAT)
                    .networkSynchronized(ByteBufCodecs.FLOAT)
            );

    // FE Energy storage for powered shields
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_ENERGY =
            REGISTER.registerComponentType("stored_energy", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
            );
}
