package io.github.lounode.extrabotany.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import io.github.lounode.extrabotany.common.item.relic.ExcaliburItem;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class LeftClickPacketExcalibur extends LeftClickPack {
	public static final LeftClickPacketExcalibur INSTANCE = new LeftClickPacketExcalibur();
	public static final ResourceLocation ID = prefix("lc");
	public static final CustomPacketPayload.Type<LeftClickPacketExcalibur> TYPE = new CustomPacketPayload.Type<>(ID);
	public static final StreamCodec<FriendlyByteBuf, LeftClickPacketExcalibur> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	public static LeftClickPacketExcalibur decode(FriendlyByteBuf buf) {
		return INSTANCE;
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public CustomPacketPayload.Type<LeftClickPacketExcalibur> type() {
		return TYPE;
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayer player) {
		float scale = player.getAttackStrengthScale(0F);
		server.execute(() -> ExcaliburItem.trySpawnBurst(player, scale));
	}
}
