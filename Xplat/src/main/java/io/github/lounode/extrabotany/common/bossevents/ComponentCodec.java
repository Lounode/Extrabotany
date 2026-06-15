package io.github.lounode.extrabotany.common.bossevents;

import com.mojang.serialization.Codec;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

public class ComponentCodec {
	public static final Codec<Component> CODEC = ComponentSerialization.CODEC;

	public static Component fromNetwork(FriendlyByteBuf buf) {
		return buf.readJsonWithCodec(CODEC);
	}

	public static void toNetwork(FriendlyByteBuf buf, Component component) {
		buf.writeJsonWithCodec(CODEC, component);
	}
}
