package org.xiyu.spartanshieldsunofficial.api.shield;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import org.xiyu.spartanshieldsunofficial.api.resource.IResourceType;
import org.xiyu.spartanshieldsunofficial.item.GeneratedBasicShieldItem;
import org.xiyu.spartanshieldsunofficial.item.GeneratedResourceShieldItem;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;

/**
 * 盾牌建造者 — 流式创建任意类型盾牌（含安全校验）。
 *
 * <h3>使用示例</h3>
 * <pre>{@code
 * DeferredHolder<Item, ?> MY_SHIELD = ITEMS.register("my_shield",
 *     ShieldBuilder.create()
 *         .material(myMaterial)
 *         .type(ShieldType.TOWER)
 *         .blockEffect(MobEffects.POISON, 60, 0)
 *         .build()
 * );
 * }</pre>
 */
public class ShieldBuilder {

    private IShieldMaterial material;
    private ShieldType type = ShieldType.BASIC;
    private boolean bashEnabled = true;
    private final List<BlockEffectEntry> blockEffects = new ArrayList<>();
    private final List<IShieldBlockHandler> blockHandlers = new ArrayList<>();
    private IResourceType resourceType;
    private int capacity;
    private int maxReceive;

    /**
     * 格挡效果条目（药水效果）
     */
    public record BlockEffectEntry(Holder<MobEffect> effect, int ticks, int amplifier) {}

    private ShieldBuilder() {}

    /** 开始构建 */
    public static ShieldBuilder create() {
        return new ShieldBuilder();
    }

    // ===== 基础属性 =====

    /** 设置材质（耐久型盾牌必需） */
    public ShieldBuilder material(IShieldMaterial material) {
        this.material = material;
        return this;
    }

    /** 设置盾牌类型，默认 BASIC */
    public ShieldBuilder type(ShieldType type) {
        this.type = type;
        return this;
    }

    /**
     * 设置是否启用盾牌猛击，默认为 {@code true}。
     * <p>
     * 启用后，通过 API 创建的盾牌无需手动添加数据包 Tag 即可获得猛击功能。
     * 设为 {@code false} 可禁用猛击（例如纯防御型盾牌）。
     * </p>
     */
    public ShieldBuilder bashable(boolean enabled) {
        this.bashEnabled = enabled;
        return this;
    }

    // ===== 格挡行为（可叠加）=====

    /** 格挡时给攻击者施加状态效果（可多次调用，效果叠加） */
    public ShieldBuilder blockEffect(Holder<MobEffect> effect, int ticks, int amplifier) {
        this.blockEffects.add(new BlockEffectEntry(effect, ticks, amplifier));
        return this;
    }

    /** 格挡时执行自定义逻辑（可多次调用，所有 handler 依序执行） */
    public ShieldBuilder blockHandler(IShieldBlockHandler handler) {
        this.blockHandlers.add(handler);
        return this;
    }

    // ===== 资源供能（可选）=====

    /**
     * 使盾牌由某种资源供能（替代耐久消耗）。
     *
     * @param resourceType 资源类型（从 ResourceRegistry 获取或自定义实例）
     * @param capacity     最大容量
     * @param maxReceive   每 tick 最大接收量
     */
    public ShieldBuilder poweredBy(IResourceType resourceType, int capacity, int maxReceive) {
        this.resourceType = resourceType;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        return this;
    }

    // ===== 构建 =====

    /**
     * 构建为 Item Supplier，可直接传给 {@code DeferredRegister.register()}。
     * <p>
     * <b>内置安全校验</b>：如果配置不合法，会在此处立即抛出异常。
     * </p>
     *
     * @throws IllegalStateException 校验失败时
     */
    public Supplier<? extends ShieldBaseItem> build() {
        // 第一步：安全校验
        validate();

        // 第二步：构建 Item Supplier
        if (this.resourceType != null) {
            // 使用一个临时引用来让 lambda 闭包正确捕获
            final IResourceType rt = this.resourceType;
            final int cap = this.capacity;
            final int mr = this.maxReceive;
            final ShieldType st = this.type;
            final List<BlockEffectEntry> effects = List.copyOf(this.blockEffects);
            final List<IShieldBlockHandler> handlers = List.copyOf(this.blockHandlers);

            // Item 会在构造器中自注册到 BuilderRegistry（DeferredRegister 创建时，注册表未冻结）
            final boolean bash = this.bashEnabled;
            Supplier<? extends ShieldBaseItem> supplier = () ->
                new GeneratedResourceShieldItem(rt, cap, mr, st, effects, handlers, bash);

            return supplier;
        } else {
            final IShieldMaterial mat = this.material;
            final ShieldType st = this.type;
            final boolean bash = this.bashEnabled;
            final List<BlockEffectEntry> effects = List.copyOf(this.blockEffects);
            final List<IShieldBlockHandler> handlers = List.copyOf(this.blockHandlers);

            return () -> new GeneratedBasicShieldItem(mat, st, effects, handlers, bash);
        }
    }

    private void validate() {
        if (this.material == null && this.resourceType == null) {
            throw new IllegalStateException(
                "Shield must have either a material (durability-based) or a resource type (energy-based). " +
                "Call .material() or .poweredBy() before .build()."
            );
        }
        if (this.material != null && this.resourceType != null) {
            throw new IllegalStateException(
                "Shield cannot have both material AND resource type. " +
                "A shield is either durability-based (.material()) or resource-based (.poweredBy()), not both."
            );
        }
        if (this.resourceType != null) {
            if (this.capacity <= 0) {
                throw new IllegalStateException("poweredBy capacity must be > 0, got: " + this.capacity);
            }
            if (this.maxReceive <= 0) {
                throw new IllegalStateException("poweredBy maxReceive must be > 0, got: " + this.maxReceive);
            }
        }
    }

    // ===== Getters（供内部使用）=====

    public IShieldMaterial getMaterial() { return material; }
    public ShieldType getType() { return type; }
    public List<BlockEffectEntry> getBlockEffects() { return blockEffects; }
    public List<IShieldBlockHandler> getBlockHandlers() { return blockHandlers; }
    public IResourceType getResourceType() { return resourceType; }
    public int getCapacity() { return capacity; }
    public int getMaxReceive() { return maxReceive; }
}
