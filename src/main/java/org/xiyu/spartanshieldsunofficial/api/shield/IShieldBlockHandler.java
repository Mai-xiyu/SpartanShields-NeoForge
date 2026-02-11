package org.xiyu.spartanshieldsunofficial.api.shield;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 盾牌格挡行为回调。
 * <p>
 * {@code @FunctionalInterface} → 支持 Lambda。
 * 比如给攻击者点火：{@code (shield, player, attacker, dmg) -> attacker.igniteForSeconds(3)}
 * </p>
 * <p>
 * <b>注意</b>：{@code attacker} 类型为 {@link LivingEntity}，
 * 与内部 {@code CommonEventHandler} 的实际检查逻辑一致（只有 LivingEntity 才触发格挡回调）。
 * </p>
 */
@FunctionalInterface
public interface IShieldBlockHandler {

    /**
     * 盾牌成功格挡攻击时调用（伤害 ≥ 3.0 触发）。
     *
     * @param shield   盾牌 ItemStack
     * @param player   持盾玩家
     * @param attacker 攻击者（始终为 LivingEntity）
     * @param damage   原始伤害值
     */
    void onBlock(ItemStack shield, Player player, LivingEntity attacker, float damage);
}
