package io.github.lounode.extrabotany.network.clientbound;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public record ManaReaderPacket(int mana) implements ExtrabotanyPacket {
	public static final ResourceLocation ID = prefix("mrd");
	public static final CustomPacketPayload.Type<ManaReaderPacket> TYPE = new CustomPacketPayload.Type<>(ID);
	public static final StreamCodec<FriendlyByteBuf, ManaReaderPacket> STREAM_CODEC =
			StreamCodec.ofMember(ManaReaderPacket::encode, ManaReaderPacket::decode);

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(mana());
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public CustomPacketPayload.Type<ManaReaderPacket> type() {
		return TYPE;
	}

	public static ManaReaderPacket decode(FriendlyByteBuf buf) {
		return new ManaReaderPacket(buf.readInt());
	}

	public static class Handler {
		public static void handle(ManaReaderPacket packet) {
			int mana = packet.mana();
			Minecraft.getInstance().execute(() -> {
				Minecraft client = Minecraft.getInstance();
				client.player.displayClientMessage(
						Component.translatable("message.extrabotany.actionbar.mana_left", mana), true
				);
			});
		}
	}
}
