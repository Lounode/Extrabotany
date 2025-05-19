package io.github.lounode.extrabotany.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;


import io.netty.buffer.Unpooled;

public interface ExtrabotanyPacket {
	default FriendlyByteBuf toBuf() {
		var ret = new FriendlyByteBuf(Unpooled.buffer());
		encode(ret);
		return ret;
	}

	void encode(FriendlyByteBuf buf);

	ResourceLocation getFabricId();
}
