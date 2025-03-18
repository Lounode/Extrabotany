package io.github.lounode.extrabotany.xplat;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import net.minecraft.world.entity.player.Player;
import vazkii.botania.api.ServiceUtil;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.function.Supplier;

public interface EXplatAbstractions extends XplatAbstractions {
    EXplatAbstractions INSTANCE = (EXplatAbstractions) ServiceUtil.findService(EXplatAbstractions.class, (Supplier)null);

    void sendToPlayer(Player player, ExtrabotanyPacket packet);
}
