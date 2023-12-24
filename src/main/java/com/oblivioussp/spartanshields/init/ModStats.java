package com.oblivioussp.spartanshields.init;

import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class ModStats 
{
	public static final ResourceLocation SHIELD_BASH_HITS = new ResourceLocation(ModSpartanShields.ID, "shield_bash_hits");
	
	public static void register()
	{
		registerStat(SHIELD_BASH_HITS, StatFormatter.DEFAULT);
	}
	
	private static void registerStat(ResourceLocation statNameIn, StatFormatter formatterIn)
	{
		String name = statNameIn.toString();
		Registry.register(Registry.CUSTOM_STAT, name, statNameIn);
		Stats.CUSTOM.get(statNameIn);
	}
}
