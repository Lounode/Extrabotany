package io.github.lounode.extrabotany.forge.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import vazkii.botania.network.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import io.github.lounode.extrabotany.client.gui.HUD;
import io.github.lounode.extrabotany.network.clientbound.ColorfulBossEventPacket;
import io.github.lounode.extrabotany.network.clientbound.GaiaBossEventPacket;
import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import io.github.lounode.extrabotany.network.clientbound.SpawnGaiaPacket;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketExcalibur;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketJingwei;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketVoidArchives;

public class ForgePacketHandler {
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			prefix("main"),
			() -> "0",
			"0"::equals,
			"0"::equals);

	public static void init() {
		int i = 0;
		//ServerBound
		CHANNEL.registerMessage(i++, LeftClickPacketExcalibur.class, LeftClickPacketExcalibur::encode, LeftClickPacketExcalibur::decode,
				makeServerBoundHandler(LeftClickPacketExcalibur::handle));
		CHANNEL.registerMessage(i++, LeftClickPacketJingwei.class, LeftClickPacketJingwei::encode, LeftClickPacketJingwei::decode,
				makeServerBoundHandler(LeftClickPacketJingwei::handle));
		CHANNEL.registerMessage(i++, LeftClickPacketVoidArchives.class, LeftClickPacketVoidArchives::encode, LeftClickPacketVoidArchives::decode,
				makeServerBoundHandler(LeftClickPacketVoidArchives::handle));
		//ClientBound
		CHANNEL.registerMessage(i++, ManaReaderPacket.class, ManaReaderPacket::encode, ManaReaderPacket::decode,
				makeClientBoundHandler(ManaReaderPacket.Handler::handle));
		CHANNEL.registerMessage(i++, SpawnGaiaPacket.class, SpawnGaiaPacket::encode, SpawnGaiaPacket::decode,
				makeClientBoundHandler(SpawnGaiaPacket.Handler::handle));
		CHANNEL.registerMessage(i++, ColorfulBossEventPacket.class, ColorfulBossEventPacket::encode, ColorfulBossEventPacket::decode,
				makeClientBoundHandler((packet) -> HUD.getInstance().getBossOverlay().update(packet)));
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

	private static <T> BiConsumer<T, Supplier<NetworkEvent.Context>> makeServerBoundHandler(TriConsumer<T, MinecraftServer, ServerPlayer> handler) {
		return (m, ctx) -> {
			handler.accept(m, ctx.get().getSender().getServer(), ctx.get().getSender());
			ctx.get().setPacketHandled(true);
		};
	}

	private static <T> BiConsumer<T, Supplier<NetworkEvent.Context>> makeClientBoundHandler(Consumer<T> consumer) {
		return (m, ctx) -> {
			consumer.accept(m);
			ctx.get().setPacketHandled(true);
		};
	}
}
