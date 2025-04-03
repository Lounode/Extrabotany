package io.github.lounode.extrabotany.fabric.network;

import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import vazkii.botania.network.TriConsumer;

import java.util.function.Consumer;
import java.util.function.Function;

public class FabricPacketHandler {
    private FabricPacketHandler() {}
    public static void init() {

    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(ManaReaderPacket.ID, makeClientBoundHandler(ManaReaderPacket::decode, ManaReaderPacket.Handler::handle));
    }

    private static <T> ServerPlayNetworking.PlayChannelHandler makeServerBoundHandler(Function<FriendlyByteBuf, T> decoder, TriConsumer<T, MinecraftServer, ServerPlayer> handle) {
        return (server, player, _handler, buf, _responseSender) -> handle.accept(decoder.apply(buf), server, player);
    }

    private static <T> ClientPlayNetworking.PlayChannelHandler makeClientBoundHandler(Function<FriendlyByteBuf, T> decoder, Consumer<T> handler) {
        return (_client, _handler, buf, _responseSender) -> handler.accept(decoder.apply(buf));
    }
}
