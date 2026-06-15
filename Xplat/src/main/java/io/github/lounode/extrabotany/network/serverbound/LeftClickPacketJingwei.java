package io.github.lounode.extrabotany.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class LeftClickPacketJingwei extends LeftClickPack {
	public static final LeftClickPacketJingwei INSTANCE = new LeftClickPacketJingwei();

	public static final ResourceLocation ID = prefix("lcj");
	public static final CustomPacketPayload.Type<LeftClickPacketJingwei> TYPE = new CustomPacketPayload.Type<>(ID);
	public static final StreamCodec<FriendlyByteBuf, LeftClickPacketJingwei> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	public static LeftClickPacketJingwei decode(FriendlyByteBuf buf) {
		return INSTANCE;
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public CustomPacketPayload.Type<LeftClickPacketJingwei> type() {
		return TYPE;
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayer player) {
		float scale = player.getAttackStrengthScale(0F);
		server.execute(() -> FeatherOfJingweiItem.trySpawnAuraFire(player, scale));
	}

}
