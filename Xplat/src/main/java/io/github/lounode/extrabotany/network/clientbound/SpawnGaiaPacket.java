package io.github.lounode.extrabotany.network.clientbound;

import io.github.lounode.extrabotany.api.gaia.GaiaArena;
import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public record SpawnGaiaPacket (ClientboundAddEntityPacket inner, Gaia.GaiaSpawnData data) implements ExtrabotanyPacket {
    public static final ResourceLocation ID = prefix("spg");

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        inner().write(buf);
        buf.writeVarInt(data().getPlayerCount());
        buf.writeGlobalPos(data().getHome());
        //buf.writeUUID(data().getBossInfoUUID());
        buf.writeJsonWithCodec(GaiaArena.CODEC, data().getArena());
    }

    public static SpawnGaiaPacket decode(FriendlyByteBuf buf) {
        var inner = new ClientboundAddEntityPacket(buf);
        var data = new Gaia.GaiaSpawnData();
        data.setPlayerCount(buf.readVarInt());
        data.setHome(buf.readGlobalPos());
        //data.setBossInfoUUID(buf.readUUID());
        data.setArena(buf.readJsonWithCodec(GaiaArena.CODEC));
        return new SpawnGaiaPacket(inner, data);
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
