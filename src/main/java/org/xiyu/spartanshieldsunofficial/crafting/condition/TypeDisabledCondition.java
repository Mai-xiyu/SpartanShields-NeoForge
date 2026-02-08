package org.xiyu.spartanshieldsunofficial.crafting.condition;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class TypeDisabledCondition implements ICondition {
    public static final List<String> disabledRecipeTypes = new ArrayList<>();

    // Values
    public static final String MODDED = "modded";
    public static final String OBSIDIAN = "obsidian";
    public static final String COPPER = "copper";
    public static final String TIN = "tin";
    public static final String BRONZE = "bronze";
    public static final String STEEL = "steel";
    public static final String SILVER = "silver";
    public static final String ELECTRUM = "electrum";
    public static final String LEAD = "lead";
    public static final String NICKEL = "nickel";
    public static final String INVAR = "invar";
    public static final String CONSTANTAN = "constantan";
    public static final String PLATINUM = "platinum";
    public static final String ALUMINUM = "aluminum";
    public static final String SIGNALUM = "signalum";
    public static final String LUMIUM = "lumium";
    public static final String ENDERIUM = "enderium";

    public static final MapCodec<TypeDisabledCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.listOf().fieldOf("disabled").forGetter(condition -> condition.types)
            ).apply(instance, TypeDisabledCondition::new)
    );
    private final List<String> types;

    public TypeDisabledCondition(List<String> types) {
        this.types = types;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        for (String type : this.types) {
            if (disabledRecipeTypes.contains(type))
                return false;
        }
        return true;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public static void clear() {
        disabledRecipeTypes.clear();
    }

    public static void updateType(String type, boolean disabled) {
        boolean containsValue = disabledRecipeTypes.contains(type);
        if (!containsValue && disabled)
            disabledRecipeTypes.add(type);
        else if (containsValue)
            disabledRecipeTypes.remove(type);
    }
}
