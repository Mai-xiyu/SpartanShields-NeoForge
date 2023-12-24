package com.oblivioussp.spartanshields.crafting.condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class TypeDisabledCondition implements ICondition 
{
	private static final ResourceLocation NAME = new ResourceLocation(ModSpartanShields.ID, "type_disabled");
	private static final Set<String> disabledRecipeTypes = new HashSet<String>();
	
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
	
	private final List<String> types;
	
	public TypeDisabledCondition(List<String> types)
	{
		this.types = types;
	}
	
	@Override
	public ResourceLocation getID() 
	{
		return NAME;
	}

	@Override
	public boolean test(IContext context) 
	{
		for(String type : types)
		{
			if(disabledRecipeTypes.contains(type))
				return false;
		}
		return true;
	}
	
	@Override
//	@Deprecated(forRemoval = true, since = "1.18.2")
	public boolean test() 
	{
		return false;
	}

	public static class Serializer implements IConditionSerializer<TypeDisabledCondition>
	{
		public static final Serializer INSTANCE = new Serializer();
		
		@Override
		public void write(JsonObject json, TypeDisabledCondition value) 
		{
			JsonArray array = new JsonArray();
			for(String type : value.types)
			{
				array.add(type);
			}
			json.add("disabled", array);
		}

		@Override
		public TypeDisabledCondition read(JsonObject json) 
		{
			JsonArray array = GsonHelper.getAsJsonArray(json, "disabled");
			List<String> typeList = new ArrayList<String>();
			for(int i = 0; i < array.size(); i++)
			{
				String str = array.get(i).getAsString();
				typeList.add(str);
			}
			return new TypeDisabledCondition(typeList);
		}

		@Override
		public ResourceLocation getID()
		{
			return TypeDisabledCondition.NAME;
		}
	}
	
	public static void clear()
	{
		disabledRecipeTypes.clear();
	}
	
	public static void updateType(String type, boolean disabled)
	{
		boolean containsValue = disabledRecipeTypes.contains(type);
		if(!containsValue && disabled)
			disabledRecipeTypes.add(type);
		else if(containsValue)
			disabledRecipeTypes.remove(type);
	}
}
