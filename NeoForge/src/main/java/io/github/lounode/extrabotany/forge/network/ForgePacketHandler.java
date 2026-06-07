package io.github.lounode.extrabotany.forge.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import io.github.lounode.extrabotany.client.gui.HUD;
import io.github.lounode.extrabotany.network.clientbound.ColorfulBossEventPacket;
import io.github.lounode.extrabotany.network.clientbound.GaiaBossEventPacket;
import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import io.github.lounode.extrabotany.network.clientbound.SpawnGaiaPacket;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketExcalibur;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketJingwei;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketVoidArchives;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ForgePacketHandler {
	public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");

		registrar.playToServer(LeftClickPacketExcalibur.TYPE, LeftClickPacketExcalibur.STREAM_CODEC, makeServerBoundHandler(LeftClickPacketExcalibur::handle));
		registrar.playToServer(LeftClickPacketJingwei.TYPE, LeftClickPacketJingwei.STREAM_CODEC, makeServerBoundHandler(LeftClickPacketJingwei::handle));
		registrar.playToServer(LeftClickPacketVoidArchives.TYPE, LeftClickPacketVoidArchives.STREAM_CODEC, makeServerBoundHandler(LeftClickPacketVoidArchives::handle));

		registrar.playToClient(ManaReaderPacket.TYPE, ManaReaderPacket.STREAM_CODEC, makeClientBoundHandler(() -> (packet, player) -> ManaReaderPacket.Handler.handle(packet)));
		registrar.playToClient(SpawnGaiaPacket.TYPE, SpawnGaiaPacket.STREAM_CODEC, makeClientBoundHandler(() -> (packet, player) -> SpawnGaiaPacket.Handler.handle(packet)));
		registrar.playToClient(ColorfulBossEventPacket.TYPE, ColorfulBossEventPacket.STREAM_CODEC,
				makeClientBoundHandler(() -> (packet, player) -> HUD.getInstance().getBossOverlay().update(packet)));

		registerOperation();
	}

	private static void registerOperation() {
		ColorfulBossEventPacket.Operation.register("add", () -> ColorfulBossEventPacket.AddOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("remove", () -> ColorfulBossEventPacket.RemoveOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_progress", () -> ColorfulBossEventPacket.UpdateProgressOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_name", () -> ColorfulBossEventPacket.UpdateNameOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_style", () -> ColorfulBossEventPacket.UpdateStyleOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_properties", () -> ColorfulBossEventPacket.UpdatePropertiesOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_player_count", () -> GaiaBossEventPacket.UpdatePlayerCountOperation.CODEC);
		ColorfulBossEventPacket.Operation.register("update_grain_time", () -> GaiaBossEventPacket.UpdateGrainTimeOperation.CODEC);
	}

	private static <T extends CustomPacketPayload> IPayloadHandler<T> makeServerBoundHandler(ServerboundHandler<T> handler) {
		return (packet, context) -> {
			ServerPlayer player = (ServerPlayer) context.player();
			handler.accept(packet, player.getServer(), player);
		};
	}

	private static <T extends CustomPacketPayload> IPayloadHandler<T> makeClientBoundHandler(Supplier<BiConsumer<T, Player>> consumerGetter) {
		return (packet, context) -> consumerGetter.get().accept(packet, context.player());
	}

	@FunctionalInterface
	private interface ServerboundHandler<T> {
		void accept(T packet, net.minecraft.server.MinecraftServer server, ServerPlayer player);
	}
}
