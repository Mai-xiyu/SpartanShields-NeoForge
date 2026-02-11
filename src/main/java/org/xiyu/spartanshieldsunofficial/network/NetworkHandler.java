package org.xiyu.spartanshieldsunofficial.network;

import org.xiyu.spartanshieldsunofficial.ModSpartanShields;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    private static final int PROTOCOL_VERSION = 1;
    
    public static final SimpleChannel CHANNEL = ChannelBuilder
            .named(ResourceLocation.tryBuild(ModSpartanShields.ID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();
    
    public static void register() {
        CHANNEL.messageBuilder(ShieldBashPacket.class)
                .encoder(ShieldBashPacket::encode)
                .decoder(ShieldBashPacket::decode)
                .consumerMainThread(ShieldBashPacket::handle)
                .add();
    }

    public static void sendPacketTo(ShieldBashPacket packet, ServerPlayer player) {
        CHANNEL.send(packet, PacketDistributor.PLAYER.with(player));
    }

    public static void sendPacketToServer(ShieldBashPacket packet) {
        CHANNEL.send(packet, PacketDistributor.SERVER.noArg());
    }
}
