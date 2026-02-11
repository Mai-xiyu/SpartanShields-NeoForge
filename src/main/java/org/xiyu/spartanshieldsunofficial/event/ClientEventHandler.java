package org.xiyu.spartanshieldsunofficial.event;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.client.ModKeyBinds;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.network.NetworkHandler;
import org.xiyu.spartanshieldsunofficial.network.ShieldBashPacket;
import org.xiyu.spartanshieldsunofficial.tags.ModItemTags;
import org.xiyu.spartanshieldsunofficial.util.Log;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ModSpartanShields.ID, value = Dist.CLIENT)
public class ClientEventHandler {

    /**
     * 判断盾牌是否允许猛击：通过 Tag 或通过 API 的 bashable 标志。
     */
    private static boolean isBashAllowed(ItemStack stack) {
        return stack.is(ModItemTags.SHIELDS_WITH_BASH)
            || (stack.getItem() instanceof ShieldBaseItem shield && shield.isBashable());
    }

    @SubscribeEvent
    public static void onMouseInputEvent(InputEvent.MouseButton.Post ev) {
        checkForShieldBash();
    }

    @SubscribeEvent
    public static void onKeyboardInputEvent(InputEvent.Key ev) {
        checkForShieldBash();
    }

    protected static void checkForShieldBash() {
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;

        // Ensure the following
        // - Shield Bashing is NOT disabled
        // - The game is NOT paused
        // - The game is NOT in any GUI
        // - The game is loaded into a world
        // - The player is valid. If there is no valid player, do not execute this event as it will cause a crash
        // If not, then don't continue the attack
        if (Config.INSTANCE.disableShieldBash.get() || mc.level == null || mc.screen != null || Minecraft.getInstance().isPaused() || player == null)
            return;

        // Ensure that the player is blocking first
        if (player.isBlocking()) {
            ItemStack shieldStack;
            InteractionHand shieldHand;
            ItemStack usedItem = player.getUseItem();
            // NOTE: To prevent erroneous hand swinging, the attack keybind needs to be 'consumed' so it isn't used after this
            if (isBashAllowed(usedItem) && usedItem.canPerformAction(ToolActions.SHIELD_BLOCK) &&
                    (ModKeyBinds.KEY_ALT_SHIELD_BASH.isUnbound() ? mc.options.keyAttack.consumeClick() : ModKeyBinds.KEY_ALT_SHIELD_BASH.isDown())) {
                shieldStack = player.getUseItem();
                shieldHand = player.getUsedItemHand();
            } else
                return;

//			Log.info("Bashing hand: " + shieldHand);
            if (player.getCooldowns().isOnCooldown(shieldStack.getItem()))
                return;

            assert mc.player != null;
            HitResult result = getEntityMouseOverExtended(mc.player.entityInteractionRange());

            if (result != null) {
                int entId = -1;
                boolean attackEntity = true;
                EntityHitResult entityRayTrace = null;
                if (result instanceof EntityHitResult)
                    entityRayTrace = (EntityHitResult) result;

                if (entityRayTrace != null && entityRayTrace.getEntity() != player) {
                    Log.debug("Hit Entity with Shield Bash! - " + entityRayTrace.getEntity());
                    entId = entityRayTrace.getEntity().getId();
                }

                if (entId == -1) {
                    entId = 0;
                    attackEntity = false;
                    //Log.debug("Shield Bash has missed!");
                }

                player.swing(shieldHand, true);
//				Log.debug("Shield Hand: " + shieldHand.toString());
                NetworkHandler.sendPacketToServer(new ShieldBashPacket(shieldHand, entId, attackEntity));
            }
        }
    }

    private static HitResult getEntityMouseOverExtended(double reach) {
        HitResult result = null;
        Minecraft mc = Minecraft.getInstance();
        Entity viewEntity = mc.getCameraEntity();

        if (viewEntity != null && mc.level != null) {
            HitResult rayTrace = viewEntity.pick(reach, 0.0f, false);
            Vec3 eyePos = viewEntity.getEyePosition(0.0f);
            boolean flag = false;
            double d1 = rayTrace.getLocation().distanceToSqr(eyePos);

            Vec3 lookVec = viewEntity.getViewVector(1.0f);
            Vec3 attackVec = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);

            AABB expBounds = viewEntity.getBoundingBox().expandTowards(lookVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entityRayTrace = ProjectileUtil.getEntityHitResult(viewEntity, eyePos, attackVec, expBounds, entity -> !entity.isSpectator() && entity.isPickable(), d1);

            result = Objects.requireNonNullElseGet(entityRayTrace, () -> BlockHitResult.miss(attackVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), BlockPos.containing(attackVec)));
        }

        return result;
    }

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent ev) {
//		Player player = ev.getPlayer();
        ItemStack stack = ev.getItemStack();
        if (!stack.isEmpty() && !Config.INSTANCE.disableShieldBash.get() && isBashAllowed(stack) && stack.canPerformAction(ToolActions.SHIELD_BLOCK)) {
            KeyMapping boundKey = ModKeyBinds.KEY_ALT_SHIELD_BASH.isUnbound() ? Minecraft.getInstance().options.keyAttack : ModKeyBinds.KEY_ALT_SHIELD_BASH;
            ev.getToolTip().add(1, Component.translatable("tooltip." + ModSpartanShields.ID + ".shield_bash",
                    Component.translatable("tooltip." + ModSpartanShields.ID + ".shield_bash.value",
                            Component.translatable(boundKey.getTranslatedKeyMessage().getString().toUpperCase()).withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GOLD));
        }
    }
}
