package io.github.lounode.extrabotany.network.clientbound;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import io.github.lounode.extrabotany.api.gaia.GaiaArena;
import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public record SpawnGaiaPacket(ClientboundAddEntityPacket inner, Gaia.GaiaSpawnData data) implements ExtrabotanyPacket {

	public static final ResourceLocation ID = prefix("spg");
	public static final CustomPacketPayload.Type<SpawnGaiaPacket> TYPE = new CustomPacketPayload.Type<>(ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, SpawnGaiaPacket> STREAM_CODEC =
			StreamCodec.ofMember(SpawnGaiaPacket::encode, SpawnGaiaPacket::decode);

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public CustomPacketPayload.Type<SpawnGaiaPacket> type() {
		return TYPE;
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		RegistryFriendlyByteBuf registryBuf = requireRegistryBuffer(buf);
		ClientboundAddEntityPacket.STREAM_CODEC.encode(registryBuf, inner());
		registryBuf.writeVarInt(data().getPlayerCount());
		registryBuf.writeGlobalPos(data().getHome());
		//buf.writeUUID(data().getBossInfoUUID());
		registryBuf.writeJsonWithCodec(GaiaArena.CODEC, data().getArena());
	}

	public static SpawnGaiaPacket decode(FriendlyByteBuf buf) {
		RegistryFriendlyByteBuf registryBuf = requireRegistryBuffer(buf);
		var inner = ClientboundAddEntityPacket.STREAM_CODEC.decode(registryBuf);
		var data = new Gaia.GaiaSpawnData();
		data.setPlayerCount(registryBuf.readVarInt());
		data.setHome(registryBuf.readGlobalPos());
		//data.setBossInfoUUID(buf.readUUID());
		data.setArena(registryBuf.readJsonWithCodec(GaiaArena.CODEC));
		return new SpawnGaiaPacket(inner, data);
	}

	private static RegistryFriendlyByteBuf requireRegistryBuffer(FriendlyByteBuf buf) {
		if (buf instanceof RegistryFriendlyByteBuf registryBuf) {
			return registryBuf;
		}
		throw new IllegalArgumentException("SpawnGaiaPacket requires a registry-aware network buffer");
	}

	public static class Handler {
		public static void handle(SpawnGaiaPacket packet) {
			var inner = packet.inner();
			var data = packet.data();

			Minecraft.getInstance().execute(() -> {
				var player = Minecraft.getInstance().player;
				if (player != null) {
					player.connection.handleAddEntity(inner);
					Entity e = player.level().getEntity(inner.getId());
					if (e instanceof Gaia gaia) {
						gaia.syncDataFormServer(data);
					}
				}
			});
		}
	}
}
