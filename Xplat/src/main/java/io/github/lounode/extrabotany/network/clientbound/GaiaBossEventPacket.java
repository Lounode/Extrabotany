package io.github.lounode.extrabotany.network.clientbound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.BossEvent;

import java.util.UUID;

import io.github.lounode.extrabotany.common.bossevents.GaiaBossEvent;

public class GaiaBossEventPacket {
	public static ColorfulBossEventPacket createPlayersPacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdatePlayerCountOperation(((GaiaBossEvent) event).getPlayerCount()));
	}

	public static ColorfulBossEventPacket createGrainPacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdateGrainTimeOperation(((GaiaBossEvent) event).getGrainTime()));
	}

	public record UpdatePlayerCountOperation(int playerCount) implements ColorfulBossEventPacket.Operation {
		public static final Codec<UpdatePlayerCountOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdatePlayerCountOperation::getType),
				Codec.INT.fieldOf("playerCount").forGetter(UpdatePlayerCountOperation::playerCount))
				.apply(instance, (type, count) -> new UpdatePlayerCountOperation(count))
		);

		@Override
		public String getType() {
			return "update_player_count";
		}

		@Override
		public Codec<? extends ColorfulBossEventPacket.Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, ColorfulBossEventPacket.Handler handler) {
			((Handler) handler).updatePlayerCount(uuid, playerCount());
		}
	}

	public record UpdateGrainTimeOperation(int time) implements ColorfulBossEventPacket.Operation {
		public static final Codec<UpdateGrainTimeOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdateGrainTimeOperation::getType),
				Codec.INT.fieldOf("playerCount").forGetter(UpdateGrainTimeOperation::time))
				.apply(instance, (type, time) -> new UpdateGrainTimeOperation(time))

		);

		static {
			ColorfulBossEventPacket.Operation.register("update_grain_time", () -> CODEC);
		}

		@Override
		public Codec<? extends ColorfulBossEventPacket.Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, ColorfulBossEventPacket.Handler handler) {
			((Handler) handler).updateGrainTime(uuid, time());
		}

		@Override
		public String getType() {
			return "update_grain_time";
		}
	}

	public interface Handler extends ColorfulBossEventPacket.Handler {
		void updatePlayerCount(UUID uuid, int playerCount);
		void updateGrainTime(UUID uuid, int time);
	}
}
