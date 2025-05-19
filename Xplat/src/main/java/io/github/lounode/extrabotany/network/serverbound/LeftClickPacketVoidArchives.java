package io.github.lounode.extrabotany.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import io.github.lounode.extrabotany.common.item.relic.void_archives.variants.Excalibur;

public class LeftClickPacketVoidArchives extends LeftClickPack {

	public static final LeftClickPacketVoidArchives INSTANCE = new LeftClickPacketVoidArchives();
	public static final ResourceLocation ID = prefix("lca");

	public static LeftClickPacketVoidArchives decode(FriendlyByteBuf buf) {
		return INSTANCE;
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayer player) {
		float scale = player.getAttackStrengthScale(0F);
		server.execute(() -> Excalibur.INSTANCE.trySpawnBurst(player, scale));
	}
}
