package io.github.lounode.extrabotany.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface ExtrabotanyPacket {
    default FriendlyByteBuf toBuf() {
        var ret = new FriendlyByteBuf(Unpooled.buffer());
        encode(ret);
        return ret;
    }

    void encode(FriendlyByteBuf buf);


    ResourceLocation getFabricId();
}
