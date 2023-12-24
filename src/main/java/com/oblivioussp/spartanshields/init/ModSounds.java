package com.oblivioussp.spartanshields.init;

import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds 
{
	public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModSpartanShields.ID);
	
	public static final ResourceLocation LOC_SHIELD_BASH_HIT = new ResourceLocation(ModSpartanShields.ID, "shield.bash.hit");
	public static final ResourceLocation LOC_SHIELD_BASH_MISS = new ResourceLocation(ModSpartanShields.ID, "shield.bash.miss");
	public static final ResourceLocation LOC_SHIELD_PAYBACK_CHARGE = new ResourceLocation(ModSpartanShields.ID, "shield.payback.charge");
	
	public static final RegistryObject<SoundEvent> SHIELD_BASH_HIT = REGISTER.register("shield.bash.hit", () -> new SoundEvent(LOC_SHIELD_BASH_HIT));
	public static final RegistryObject<SoundEvent> SHIELD_BASH_MISS = REGISTER.register("shield.bash.miss", () -> new SoundEvent(LOC_SHIELD_BASH_MISS));
	public static final RegistryObject<SoundEvent> SHIELD_PAYBACK_CHARGE = REGISTER.register("shield.payback.charge", () -> new SoundEvent(LOC_SHIELD_PAYBACK_CHARGE));
}
