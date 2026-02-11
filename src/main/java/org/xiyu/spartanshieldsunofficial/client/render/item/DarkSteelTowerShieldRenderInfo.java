package org.xiyu.spartanshieldsunofficial.client.render.item;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.init.ModDataComponents;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DarkSteelTowerShieldRenderInfo extends TowerShieldRenderInfo {
    protected final float r, g, b;

    // TODO: Custom shader using the same shader code as the eye shader? (to prevent any potential screwery with shader mods)
    protected static final RenderType LIGHTS_ON = RenderType.eyes(ResourceLocation.tryBuild(ModSpartanShields.ID, "textures/entity/enderio/dark_steel_tower_shield_lights_on.png"));
    protected static final RenderType LIGHTS_OFF = RenderType.entityTranslucent(ResourceLocation.tryBuild(ModSpartanShields.ID, "textures/entity/enderio/dark_steel_tower_shield_lights_off.png"));


    public DarkSteelTowerShieldRenderInfo(float rIn, float gIn, float bIn) {
        super(ResourceLocation.tryBuild(ModSpartanShields.ID, "entity/enderio/dark_steel_tower_shield_nopattern"),
                ResourceLocation.tryBuild(ModSpartanShields.ID, "entity/enderio/dark_steel_tower_shield_pattern"));
        this.r = rIn;
        this.g = gIn;
        this.b = bIn;
    }

    @Override
    public boolean hasLayers() {
        return true;
    }

    @Override
    public RenderType getLayerRenderType(ItemStack stack) {
        boolean isPowered = ModDataComponents.getStoredEnergy(stack) != 0;
        return isPowered ? LIGHTS_ON : LIGHTS_OFF;
    }

    @Override
    public float getColourRed() {
        return this.r;
    }

    @Override
    public float getColourGreen() {
        return this.g;
    }

    @Override
    public float getColourBlue() {
        return this.b;
    }
}
