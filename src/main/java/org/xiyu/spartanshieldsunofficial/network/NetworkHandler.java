package org.xiyu.spartanshieldsunofficial.network;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ModSpartanShields.ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ModSpartanShields.ID).versioned("1");
        registrar.playToServer(
                ShieldBashPacket.TYPE,
                ShieldBashPacket.STREAM_CODEC,
                ShieldBashPacket::handle
        );
    }

    public static void sendPacketTo(ShieldBashPacket packet, ServerPlayer player) {
        if (!(player instanceof FakePlayer))
            PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendPacketToServer(ShieldBashPacket packet) {
        PacketDistributor.sendToServer(packet);
    }
}
