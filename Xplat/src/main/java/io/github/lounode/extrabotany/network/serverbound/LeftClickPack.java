package io.github.lounode.extrabotany.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;


import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

public abstract class LeftClickPack implements ExtrabotanyPacket {
	@Override
	public void encode(FriendlyByteBuf buf) {

	}

	@Override
	public abstract ResourceLocation getFabricId();

	public abstract void handle(MinecraftServer server, ServerPlayer player);
}
