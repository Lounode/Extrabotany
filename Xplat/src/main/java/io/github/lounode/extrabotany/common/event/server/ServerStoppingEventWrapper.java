package io.github.lounode.extrabotany.common.event.server;

import net.minecraft.server.MinecraftServer;

public class ServerStoppingEventWrapper {
	private final MinecraftServer server;

	public ServerStoppingEventWrapper(MinecraftServer server) {
		this.server = server;
	}

	public MinecraftServer getServer() {
		return server;
	}
}
