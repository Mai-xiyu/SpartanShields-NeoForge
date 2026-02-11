package org.xiyu.spartanshieldsunofficial.api;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.xiyu.spartanshieldsunofficial.api.client.ITowerShieldRenderer;
import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;
import org.xiyu.spartanshieldsunofficial.api.resource.ResourceRegistry;
import org.xiyu.spartanshieldsunofficial.api.shield.IShieldMaterial;
import org.xiyu.spartanshieldsunofficial.client.render.item.TowerShieldBEWLR;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

/**
 * Spartan Shields API 唯一入口。
 * <p>
 * 附属模组的所有操作都从这里出发。
 * </p>
 *
 * <h3>材质创建</h3>
 * <pre>{@code
 * IShieldMaterial mithril = SpartanShieldsAPI.createMaterial(800, 18, MyTags.MITHRIL_INGOT);
 * }</pre>
 *
 * <h3>资源类型注册</h3>
 * <pre>{@code
 * SpartanShieldsAPI.registerResourceType(new SimpleResourceType(...));
 * }</pre>
 *
 * <h3>塔盾渲染器注册</h3>
 * <pre>{@code
 * // 必须在客户端事件中调用
 * SpartanShieldsAPI.registerTowerShieldRenderer(MY_SHIELD.get(), new MyRenderer());
 * }</pre>
 */
public final class SpartanShieldsAPI {

    public static final String MOD_ID = "spartan_shields_unofficial";

    private SpartanShieldsAPI() {}

    // ===== 材质 =====

    /**
     * 创建盾牌材质。
     *
     * @param durability      基础耐久值
     * @param enchantability  附魔能力值
     * @param repairTag       铁砧修复材料 Tag
     * @return 新的盾牌材质实例
     */
    public static IShieldMaterial createMaterial(int durability, int enchantability, TagKey<Item> repairTag) {
        // 使用 TierSS 作为桥接实现
        TierSS tier = new TierSS(durability, 0.0f, 0.0f, enchantability, repairTag);
        return new IShieldMaterial() {
            @Override public int getDurability() { return tier.getUses(); }
            @Override public int getEnchantability() { return tier.getEnchantmentValue(); }
            @Override public TagKey<Item> getRepairTag() { return tier.getRepairTag(); }
        };
    }

    /**
     * 从原版 Tier 创建盾牌材质。
     *
     * @param vanillaTier 原版 Tier（如 {@code Tiers.DIAMOND}）
     * @param repairTag   铁砧修复材料 Tag
     * @return 新的盾牌材质实例
     */
    public static IShieldMaterial createMaterial(Tier vanillaTier, TagKey<Item> repairTag) {
        return createMaterial(vanillaTier.getUses(), vanillaTier.getEnchantmentValue(), repairTag);
    }

    // ===== 资源类型注册 =====

    /**
     * 注册一种新的资源类型。
     * <p><b>必须在模组构造器中调用</b>，不能延迟到其他事件阶段。</p>
     * <p>如果 ID 已被注册，会抛出 {@link IllegalArgumentException}。</p>
     *
     * @param type 要注册的资源类型
     * @throws IllegalArgumentException 当 type.getId() 已被注册时
     */
    public static void registerResourceType(IResourceType type) {
        ResourceRegistry.register(type);
    }

    /**
     * 根据 ID 获取已注册的资源类型。
     *
     * @param id 资源类型 ID
     * @return 对应的资源类型，不存在则返回 {@link Optional#empty()}
     */
    public static Optional<IResourceType> getResourceType(ResourceLocation id) {
        return ResourceRegistry.get(id);
    }

    /**
     * 获取所有已注册的资源类型。
     *
     * @return 不可变的资源类型集合
     */
    public static Collection<IResourceType> getAllResourceTypes() {
        return ResourceRegistry.getAll();
    }

    // ===== 客户端渲染注册 =====

    /**
     * 注册塔盾自定义渲染器（仅客户端调用）。
     * <p>
     * <b>⚠️ 必须在客户端事件中调用</b>（如 {@code FMLClientSetupEvent.enqueueWork()}），
     * 或在模组构造器中判断 {@code dist == Dist.CLIENT} 后调用。
     * 在服务端调用会导致 {@code ClassNotFoundException}。
     * </p>
     *
     * @param shield   塔盾物品实例
     * @param renderer 渲染器实现
     */
    @OnlyIn(Dist.CLIENT)
    public static void registerTowerShieldRenderer(Item shield, ITowerShieldRenderer renderer) {
        TowerShieldBEWLR.INSTANCE.registerRenderer(shield, renderer);
    }
}
