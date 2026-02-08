package org.xiyu.spartanshieldsunofficial.client.render.item;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.client.model.DarkSteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ElementiumTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.EnderiumShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.KiteShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.LumiumShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ManasteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.MekanismTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.ShieldBaseModel;
import org.xiyu.spartanshieldsunofficial.client.model.TerrasteelTowerShieldModel;
import org.xiyu.spartanshieldsunofficial.client.model.TowerShieldModel;
import org.xiyu.spartanshieldsunofficial.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class TowerShieldBEWLR extends BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
    public static final TowerShieldBEWLR INSTANCE = new TowerShieldBEWLR();

    private ShieldBaseModel baseShield;
    private ShieldBaseModel kiteShield;
    private ShieldBaseModel towerShield;
    private ShieldBaseModel lumiumShield;
    private ShieldBaseModel enderiumShield;
    private ShieldBaseModel manasteelShield;
    private ShieldBaseModel terrasteelShield;
    private ShieldBaseModel elementiumShield;
    private ShieldBaseModel mekanismShield;
    private ShieldBaseModel darkSteelShield;
    private Map<Item, ShieldBaseModel> modelMap;
    private final Map<Item, TowerShieldRenderInfo> renderInfoMap;

    private TowerShieldBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

        Builder<Item, TowerShieldRenderInfo> renderInfoMapBuilder = ImmutableMap.builder();
        renderInfoMapBuilder.put(ModItems.WOODEN_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_WOODEN_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.STONE_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_STONE_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.COPPER_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_COPPER_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.IRON_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_IRON_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.GOLDEN_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_GOLDEN_TOWER_SHIELD_);
        renderInfoMapBuilder.put(ModItems.DIAMOND_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_DIAMOND_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.NETHERITE_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_NETHERITE_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.OBSIDIAN_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_OBSIDIAN_TOWER_SHIELD);

        renderInfoMapBuilder.put(ModItems.TIN_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_TIN_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.BRONZE_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_BRONZE_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.STEEL_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_STEEL_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.SILVER_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_SILVER_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ELECTRUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ELECTRUM_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.LEAD_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_LEAD_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.NICKEL_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_NICKEL_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.INVAR_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_INVAR_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.CONSTANTAN_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_CONSTANTAN_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.PLATINUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_PLATINUM_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ALUMINUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ALUMINUM_TOWER_SHIELD);

        renderInfoMapBuilder.put(ModItems.SIGNALUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_SIGNALUM_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.LUMIUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_LUMIUM_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ENDERIUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ENDERIUM_TOWER_SHIELD);

        renderInfoMapBuilder.put(ModItems.MANASTEEL_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_MANASTEEL_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.TERRASTEEL_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_TERRASTEEL_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ELEMENTIUM_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ELEMENTIUM_TOWER_SHIELD);

        renderInfoMapBuilder.put(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_BASIC_MEKANISTS_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ADVANCED_MEKANISTS_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ELITE_MEKANISTS_TOWER_SHIELD);
        renderInfoMapBuilder.put(ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_ULTIMATE_MEKANISTS_TOWER_SHIELD);

        renderInfoMapBuilder.put(ModItems.DARK_STEEL_RIOT_TOWER_SHIELD.get(), TextureStitcher.RENDER_INFO_DARK_STEEL_TOWER_SHIELD);

        this.renderInfoMap = renderInfoMapBuilder.build();
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager p_172555_) {
        Minecraft mc = Minecraft.getInstance();

        // Reload models here
        this.baseShield = new ShieldBaseModel(mc.getEntityModels().bakeLayer(ModelLayers.BASE_SHIELD));
        this.kiteShield = new KiteShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.KITE_SHIELD));
        this.towerShield = new TowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.TOWER_SHIELD));
        this.lumiumShield = new LumiumShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.LUMIUM_SHIELD));
        this.enderiumShield = new EnderiumShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.ENDERIUM_SHIELD));
        this.manasteelShield = new ManasteelTowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.MANASTEEL_SHIELD));
        this.terrasteelShield = new TerrasteelTowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.TERRASTEEL_SHIELD));
        this.elementiumShield = new ElementiumTowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.ELEMENTIUM_SHIELD));
        this.mekanismShield = new MekanismTowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.MEKANISM_SHIELD));
        this.darkSteelShield = new DarkSteelTowerShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.DARK_STEEL_SHIELD));

        Builder<Item, ShieldBaseModel> modelMapBuilder = ImmutableMap.builder();
        modelMapBuilder.put(ModItems.WOODEN_TOWER_SHIELD.get(), this.baseShield);
        modelMapBuilder.put(ModItems.STONE_TOWER_SHIELD.get(), this.baseShield);
        modelMapBuilder.put(ModItems.COPPER_TOWER_SHIELD.get(), this.baseShield);
        modelMapBuilder.put(ModItems.IRON_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.GOLDEN_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.DIAMOND_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.NETHERITE_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.OBSIDIAN_TOWER_SHIELD.get(), this.baseShield);

        modelMapBuilder.put(ModItems.TIN_TOWER_SHIELD.get(), this.baseShield);
        modelMapBuilder.put(ModItems.BRONZE_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.STEEL_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.SILVER_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.ELECTRUM_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.LEAD_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.NICKEL_TOWER_SHIELD.get(), this.baseShield);
        modelMapBuilder.put(ModItems.INVAR_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.CONSTANTAN_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.PLATINUM_TOWER_SHIELD.get(), this.kiteShield);
        modelMapBuilder.put(ModItems.ALUMINUM_TOWER_SHIELD.get(), this.baseShield);

        modelMapBuilder.put(ModItems.SIGNALUM_TOWER_SHIELD.get(), this.towerShield);
        modelMapBuilder.put(ModItems.LUMIUM_TOWER_SHIELD.get(), this.lumiumShield);
        modelMapBuilder.put(ModItems.ENDERIUM_TOWER_SHIELD.get(), this.enderiumShield);

        modelMapBuilder.put(ModItems.MANASTEEL_TOWER_SHIELD.get(), this.manasteelShield);
        modelMapBuilder.put(ModItems.TERRASTEEL_TOWER_SHIELD.get(), this.terrasteelShield);
        modelMapBuilder.put(ModItems.ELEMENTIUM_TOWER_SHIELD.get(), this.elementiumShield);

        modelMapBuilder.put(ModItems.BASIC_MEKANISTS_TOWER_SHIELD.get(), this.mekanismShield);
        modelMapBuilder.put(ModItems.ADVANCED_MEKANISTS_TOWER_SHIELD.get(), this.mekanismShield);
        modelMapBuilder.put(ModItems.ELITE_MEKANISTS_TOWER_SHIELD.get(), this.mekanismShield);
        modelMapBuilder.put(ModItems.ULTIMATE_MEKANISTS_TOWER_SHIELD.get(), this.mekanismShield);

        modelMapBuilder.put(ModItems.DARK_STEEL_RIOT_TOWER_SHIELD.get(), this.darkSteelShield);

        this.modelMap = modelMapBuilder.build();
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext displayContext, @NotNull PoseStack mStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ShieldBaseModel model = this.modelMap.get(stack.getItem());
        TowerShieldRenderInfo renderInfo = this.renderInfoMap.get(stack.getItem());
        if (model != null && renderInfo != null) {
            BannerPatternLayers bannerPatterns = stack.get(DataComponents.BANNER_PATTERNS);
            DyeColor baseColor = stack.getOrDefault(DataComponents.BASE_COLOR, DyeColor.WHITE);
            boolean isBannered = bannerPatterns != null && !bannerPatterns.layers().isEmpty();
            mStack.pushPose();
            mStack.scale(1.0f, -1.0f, -1.0f);

            Material material = renderInfo.getMaterial(isBannered);
            VertexConsumer consumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, model.renderType(material.atlasLocation()), true, stack.hasFoil()));
            model.renderExtraParts(mStack, consumer, packedLight, packedOverlay, -1);
            if (isBannered) {
                BannerRenderer.renderPatterns(mStack, buffer, packedLight, packedOverlay, model.getPlate(), material, false, baseColor, bannerPatterns);
            } else
                model.getPlate().render(mStack, consumer, packedLight, packedOverlay, -1);

            if (renderInfo.hasLayers()) {
                int color = (255 << 24) | ((int) (renderInfo.getColourRed() * 255) << 16) | ((int) (renderInfo.getColourGreen() * 255) << 8) | (int) (renderInfo.getColourBlue() * 255);
                model.renderLayers(mStack, buffer, renderInfo.getLayerRenderType(stack), packedLight, packedOverlay, color);
            }

            mStack.popPose();
        }
    }
}
