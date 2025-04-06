package io.github.lounode.extrabotany.network.serverbound;

import io.github.lounode.extrabotany.common.item.relic.ExcaliburItem;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class LeftClickPacketExcalibur extends LeftClickPack {
    public static final LeftClickPacketExcalibur INSTANCE = new LeftClickPacketExcalibur();
    public static final ResourceLocation ID = prefix("lc");

    public static LeftClickPacketExcalibur decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    public void handle(MinecraftServer server, ServerPlayer player) {
        float scale = player.getAttackStrengthScale(0F);
        server.execute(() -> ExcaliburItem.trySpawnBurst(player, scale));
    }
}
