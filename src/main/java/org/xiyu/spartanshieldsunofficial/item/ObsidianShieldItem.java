package org.xiyu.spartanshieldsunofficial.item;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ObsidianShieldItem extends BasicShieldItem {
    // Resource locations for attribute modifiers
    private static final ResourceLocation MOVE_SPEED_MODIFIER = ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, "obsidian_shield_move_speed");
    private static final ResourceLocation KNOCKBACK_MODIFIER = ResourceLocation.fromNamespaceAndPath(ModSpartanShields.ID, "obsidian_shield_knockback");

    public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, Item.Properties prop) {
        super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
    }

    public ObsidianShieldItem(TierSS toolMaterial, int defaultMaxDamage, Item.Properties prop) {
        this(toolMaterial, defaultMaxDamage, false, prop);
    }
}
