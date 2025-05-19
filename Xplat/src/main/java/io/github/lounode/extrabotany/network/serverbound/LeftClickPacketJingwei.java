package io.github.lounode.extrabotany.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;

public class LeftClickPacketJingwei extends LeftClickPack {
	public static final LeftClickPacketJingwei INSTANCE = new LeftClickPacketJingwei();

	public static final ResourceLocation ID = prefix("lcj");

	public static LeftClickPacketJingwei decode(FriendlyByteBuf buf) {
		return INSTANCE;
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayer player) {
		float scale = player.getAttackStrengthScale(0F);
		server.execute(() -> FeatherOfJingweiItem.trySpawnAuraFire(player, scale));
	}

}
