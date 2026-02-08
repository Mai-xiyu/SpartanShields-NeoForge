package org.xiyu.spartanshieldsunofficial.data;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.init.ModSounds;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ModSpartanShields.ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSounds.SHIELD_BASH_HIT, SoundDefinition.definition().subtitle("subtitle.spartanshieldsunofficial.shield.bash.hit").
                with(Sound.sound(ResourceLocation.parse("item/shield/block1"), SoundType.SOUND),
                        Sound.sound(ResourceLocation.parse("item/shield/block2"), SoundType.SOUND),
                        Sound.sound(ResourceLocation.parse("item/shield/block3"), SoundType.SOUND),
                        Sound.sound(ResourceLocation.parse("item/shield/block4"), SoundType.SOUND),
                        Sound.sound(ResourceLocation.parse("item/shield/block5"), SoundType.SOUND)));

        this.add(ModSounds.LOC_SHIELD_BASH_MISS, SoundDefinition.definition().subtitle("subtitle.spartanshieldsunofficial.shield.bash.miss").
                with(Sound.sound(ResourceLocation.parse("entity/player/attack/sweep1"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep2"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep3"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep4"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep5"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep6"), SoundType.SOUND).volume(0.7d),
                        Sound.sound(ResourceLocation.parse("entity/player/attack/sweep7"), SoundType.SOUND).volume(0.7d)));

        this.add(ModSounds.SHIELD_PAYBACK_CHARGE, SoundDefinition.definition().subtitle("subtitle.spartanshieldsunofficial.shield.payback.charge").
                with(Sound.sound(ResourceLocation.parse("random/levelup"), SoundType.SOUND).pitch(0.5d)));
    }

}
