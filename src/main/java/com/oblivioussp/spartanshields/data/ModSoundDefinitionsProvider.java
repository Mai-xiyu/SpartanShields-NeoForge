package com.oblivioussp.spartanshields.data;

import com.oblivioussp.spartanshields.ModSpartanShields;
import com.oblivioussp.spartanshields.init.ModSounds;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinition.SoundType;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider 
{

	public ModSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) 
	{
		super(generator, ModSpartanShields.ID, helper);
	}

	@Override
	public void registerSounds()
	{
		add(ModSounds.SHIELD_BASH_HIT, SoundDefinition.definition().subtitle("subtitle.spartanshields.shield.bash.hit").
				with(Sound.sound(new ResourceLocation("item/shield/block1"), SoundType.SOUND),
						Sound.sound(new ResourceLocation("item/shield/block2"), SoundType.SOUND),
						Sound.sound(new ResourceLocation("item/shield/block3"), SoundType.SOUND),
						Sound.sound(new ResourceLocation("item/shield/block4"), SoundType.SOUND),
						Sound.sound(new ResourceLocation("item/shield/block5"), SoundType.SOUND)));
		
		add(ModSounds.LOC_SHIELD_BASH_MISS, SoundDefinition.definition().subtitle("subtitle.spartanshields.shield.bash.miss").
				with(Sound.sound(new ResourceLocation("entity/player/attack/sweep1"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep2"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep3"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep4"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep5"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep6"), SoundType.SOUND).volume(0.7d),
						Sound.sound(new ResourceLocation("entity/player/attack/sweep7"), SoundType.SOUND).volume(0.7d)));
		
		add(ModSounds.SHIELD_PAYBACK_CHARGE, SoundDefinition.definition().subtitle("subtitle.spartanshields.shield.payback.charge").
				with(Sound.sound(new ResourceLocation("random/levelup"), SoundType.SOUND).pitch(0.5d)));
	}

}
