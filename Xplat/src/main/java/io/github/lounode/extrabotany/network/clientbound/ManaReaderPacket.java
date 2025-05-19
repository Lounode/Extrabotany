package io.github.lounode.extrabotany.network.clientbound;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

public record ManaReaderPacket(int mana) implements ExtrabotanyPacket {
	public static final ResourceLocation ID = prefix("mrd");

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(mana());
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
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
