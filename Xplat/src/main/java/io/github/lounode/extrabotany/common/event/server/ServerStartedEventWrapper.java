package io.github.lounode.extrabotany.common.event.server;

import net.minecraft.server.MinecraftServer;

public class ServerStartedEventWrapper {
	private final MinecraftServer server;

	public ServerStartedEventWrapper(MinecraftServer server) {
		this.server = server;
	}

	public MinecraftServer getServer() {
		return server;
	}
}
