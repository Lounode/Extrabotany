package io.github.lounode.extrabotany.network.serverbound;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public abstract class LeftClickPack implements ExtrabotanyPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public abstract ResourceLocation getFabricId();

    public abstract void handle(MinecraftServer server, ServerPlayer player);
}
