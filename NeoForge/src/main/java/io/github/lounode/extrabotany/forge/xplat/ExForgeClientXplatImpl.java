package io.github.lounode.extrabotany.forge.xplat;

import net.neoforged.neoforge.network.PacketDistributor;

import vazkii.botania.neoforge.client.ForgeClientXplatImpl;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.ExClientXplatAbstractions;

public class ExForgeClientXplatImpl extends ForgeClientXplatImpl implements ExClientXplatAbstractions {
	@Override
	public void sendToServer(ExtrabotanyPacket packet) {
		PacketDistributor.sendToServer(packet);
	}
}
