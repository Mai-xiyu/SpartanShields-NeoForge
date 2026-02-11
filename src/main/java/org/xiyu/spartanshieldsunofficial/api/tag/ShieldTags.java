package org.xiyu.spartanshieldsunofficial.api.tag;

import org.xiyu.spartanshieldsunofficial.tags.ModItemTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * 公开的 TagKey 常量。
 * <p>
 * 附属模组将自己的盾牌加入这些 Tag 即可获得对应的功能支持。
 * </p>
 */
public final class ShieldTags {

    private ShieldTags() {}

    /** 所有基础盾牌 — 加入此 Tag 获得基础盾牌附魔支持 */
    public static final TagKey<Item> BASIC_SHIELDS = ModItemTags.BASIC_SHIELDS;

    /** 所有塔盾 — 加入此 Tag 获得塔盾附魔支持 */
    public static final TagKey<Item> TOWER_SHIELDS = ModItemTags.TOWER_SHIELDS;

    /** 支持盾击的盾牌 — 加入此 Tag 启用盾击功能 */
    public static final TagKey<Item> SHIELDS_WITH_BASH = ModItemTags.SHIELDS_WITH_BASH;
}
