package com.oblivioussp.spartanshields.network;

import com.oblivioussp.spartanshields.ModSpartanShields;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler
{
	protected static final String PROTOCOL_VERSION = "1";
	protected static final SimpleChannel instance = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ModSpartanShields.ID, "network"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	protected static int currentId = 0;

	public static void init()
	{
		instance.registerMessage(getNextPacketId(), ShieldBashPacket.class, ShieldBashPacket::encode, ShieldBashPacket::decode, ShieldBashPacket.Handler::handle);
	}
	
	public static int getNextPacketId()
	{
		int id = currentId;
		currentId++;
		return id;
	}

	public static void sendPacketTo(Object packet, ServerPlayer player)
	{
		if(!(player instanceof FakePlayer))
			instance.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendPacketToServer(Object packet)
	{
		instance.sendToServer(packet);
	}

}
