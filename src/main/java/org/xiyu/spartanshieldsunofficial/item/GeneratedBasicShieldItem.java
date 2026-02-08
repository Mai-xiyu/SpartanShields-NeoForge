package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.api.shield.IShieldBlockHandler;
import org.xiyu.spartanshieldsunofficial.api.shield.IShieldMaterial;
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldBuilder;
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldType;
import org.xiyu.spartanshieldsunofficial.client.ClientHelper;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * 由 {@link ShieldBuilder} 生成的耐久型盾牌 Item。
 * <p>
 * 当 Builder 配置了 {@code material}（无 {@code poweredBy}）时创建此类。
 * 支持可叠加的格挡效果和自定义格挡处理器。
 * </p>
 */
public class GeneratedBasicShieldItem extends BasicShieldItem implements IDamageShield {

    private final List<ShieldBuilder.BlockEffectEntry> blockEffects;
    private final List<IShieldBlockHandler> blockHandlers;

    public GeneratedBasicShieldItem(IShieldMaterial material, ShieldType type,
                                     List<ShieldBuilder.BlockEffectEntry> blockEffects,
                                     List<IShieldBlockHandler> blockHandlers) {
        super(
            TierSS.fromMaterial(material),
            material.getDurability(),
            type == ShieldType.TOWER,
            new Item.Properties()
        );
        this.blockEffects = blockEffects;
        this.blockHandlers = blockHandlers;

        if (FMLEnvironment.dist.isClient()) {
            ClientHelper.registerShieldPropertyOverrides(this);
        }
    }

    @Override
    public void damageShield(ItemStack shieldStack, Player player, Entity attacker, float damage) {
        if (attacker instanceof LivingEntity livingAttacker) {
            // 施加所有格挡效果
            for (ShieldBuilder.BlockEffectEntry entry : this.blockEffects) {
                livingAttacker.addEffect(new MobEffectInstance(
                    entry.effect(), entry.ticks(), entry.amplifier(), false, true
                ));
            }

            // 执行所有自定义格挡处理器
            for (IShieldBlockHandler handler : this.blockHandlers) {
                handler.onBlock(shieldStack, player, livingAttacker, damage);
            }
        }
    }

    /** 获取格挡效果列表（供 CommonEventHandler 使用） */
    public List<ShieldBuilder.BlockEffectEntry> getBlockEffects() {
        return this.blockEffects;
    }

    /** 获取格挡处理器列表（供 CommonEventHandler 使用） */
    public List<IShieldBlockHandler> getBlockHandlers() {
        return this.blockHandlers;
    }
}
