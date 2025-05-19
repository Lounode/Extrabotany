package io.github.lounode.extrabotany.forge.xplat;

import vazkii.botania.forge.client.ForgeClientXplatImpl;

import io.github.lounode.extrabotany.forge.network.ForgePacketHandler;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.ExClientXplatAbstractions;

public class ExForgeClientXplatImpl extends ForgeClientXplatImpl implements ExClientXplatAbstractions {
	@Override
	public void sendToServer(ExtrabotanyPacket packet) {
		ForgePacketHandler.CHANNEL.sendToServer(packet);
	}
}
