package io.github.lounode.extrabotany.common.util;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

import java.util.UUID;

public class PlayerUtil {
	public static Player createFakePlayer(ServerLevel level, UUID uuid) {
		return EXplatAbstractions.INSTANCE.createFakePlayer(level, new GameProfile(uuid, ExtraBotanyConfig.common().fakePlayerId()));
	}
}
