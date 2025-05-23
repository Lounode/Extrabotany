package io.github.lounode.extrabotany.network.clientbound;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public record LoootDataPacket() implements ExtrabotanyPacket {

	public static final ResourceLocation ID = prefix("ldp");

	@Override
	public void encode(FriendlyByteBuf buf) {

	}

	public static LoootDataPacket decode(FriendlyByteBuf buf) {
		return new LoootDataPacket();
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	public static class Handler {
		public static void handle(LoootDataPacket packet) {
			//int mana = packet.mana();
			Minecraft.getInstance().execute(() -> {
				/*
				Minecraft client = Minecraft.getInstance();
				client.player.displayClientMessage(
						Component.translatable("message.extrabotany.actionbar.mana_left", mana), true
				);
				*/
			});
		}
	}
}
