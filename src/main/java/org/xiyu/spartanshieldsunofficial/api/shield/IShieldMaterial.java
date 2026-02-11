package org.xiyu.spartanshieldsunofficial.api.shield;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * 盾牌材质接口。
 * <p>
 * 定义盾牌的耐久、附魔值、修复材料。
 * 内部桥接到 {@link org.xiyu.spartanshieldsunofficial.util.TierSS}。
 * </p>
 */
public interface IShieldMaterial {

    /** 基础耐久值（塔盾会自动乘以配置倍率） */
    int getDurability();

    /** 附魔能力值 */
    int getEnchantability();

    /** 铁砧修复材料 Tag */
    TagKey<Item> getRepairTag();
}
