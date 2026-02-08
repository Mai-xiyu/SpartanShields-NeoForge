package org.xiyu.spartanshieldsunofficial.api.resource;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * {@link IResourceType} 的通用实现基类。
 * <p>
 * 提供开箱即用的默认实现：
 * <ul>
 *   <li>getStored / setStored — 使用传入的 DataComponentType 自动读写</li>
 *   <li>format*Tooltip — 使用 "{stored} / {capacity} {displayName}" 格式</li>
 *   <li>getBarColor — 使用构造时传入的颜色</li>
 * </ul>
 * 附属模组只需在构造器里传入参数即可，想自定义某个方法可直接 override。
 * </p>
 *
 * <h3>使用示例</h3>
 * <pre>{@code
 * // 最简用法 — 只需 4 个参数
 * IResourceType mana = new SimpleResourceType(
 *     ResourceLocation.fromNamespaceAndPath("botania", "mana"),
 *     Component.literal("Mana"),
 *     MyDataComponents.STORED_MANA.get(),
 *     0x00C6FF
 * );
 * }</pre>
 */
public class SimpleResourceType implements IResourceType {

    private final ResourceLocation id;
    private final Component displayName;
    private final DataComponentType<Integer> dataComponent;
    private final int barColor;

    /**
     * @param id            全局唯一 ID（如 "botania:mana"）
     * @param displayName   Tooltip 中显示的单位名（如 "Mana"）
     * @param dataComponent 存储在 ItemStack 上的 DataComponentType（必须是 Integer 类型）
     * @param barColor      物品耐久条颜色（RGB, 如 0x00C6FF）
     */
    public SimpleResourceType(ResourceLocation id, Component displayName,
                               DataComponentType<Integer> dataComponent, int barColor) {
        this.id = id;
        this.displayName = displayName;
        this.dataComponent = dataComponent;
        this.barColor = barColor;
    }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public Component getDisplayName() { return displayName; }

    @Override
    public DataComponentType<Integer> getDataComponent() { return dataComponent; }

    @Override
    public int getBarColor() { return barColor; }

    @Override
    public int getStored(ItemStack stack) {
        return stack.getOrDefault(dataComponent, 0);
    }

    @Override
    public void setStored(ItemStack stack, int amount) {
        stack.set(dataComponent, Math.max(0, amount));
    }

    @Override
    public Component formatCapacityTooltip(int stored, int capacity) {
        return Component.literal(String.format("%,d / %,d ", stored, capacity))
            .append(displayName);
    }

    @Override
    public Component formatChargeRateTooltip(int maxReceive) {
        return Component.literal(String.format("%,d ", maxReceive))
            .append(displayName)
            .append(Component.literal("/t"));
    }

    @Override
    public Component formatPerDamageTooltip(int costPerDamage) {
        return Component.literal(String.format("%,d ", costPerDamage))
            .append(displayName)
            .append(Component.literal("/hit"));
    }
}
