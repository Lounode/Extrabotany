package io.github.lounode.extrabotany.xplat;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import vazkii.botania.api.ServiceUtil;
import vazkii.botania.xplat.ClientXplatAbstractions;

public interface ExClientXplatAbstractions extends ClientXplatAbstractions {
    ExClientXplatAbstractions INSTANCE = ServiceUtil.findService(ExClientXplatAbstractions.class, null);
    void sendToServer(ExtrabotanyPacket packet);
}
