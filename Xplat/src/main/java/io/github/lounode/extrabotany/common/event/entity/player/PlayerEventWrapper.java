package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class PlayerEventWrapper {
	private final Player entity;

	protected PlayerEventWrapper(Player entity) {
		this.entity = entity;
	}

	public Player getEntity() {
		return entity;
	}

	public static class PlayerLoggedOutEvent extends PlayerEventWrapper {
		public PlayerLoggedOutEvent(Player entity) {
			super(entity);
		}
	}

	public static class BreakSpeed extends PlayerEventWrapper {
		private final BlockState state;
		@Nullable
		private final BlockPos pos;
		private float newSpeed;
		private boolean canceled;

		public BreakSpeed(Player entity, BlockState state, @Nullable BlockPos pos, float newSpeed) {
			super(entity);
			this.state = state;
			this.pos = pos;
			this.newSpeed = newSpeed;
		}

		public BlockState getState() {
			return state;
		}

		@Nullable
		public BlockPos getPos() {
			return pos;
		}

		public float getNewSpeed() {
			return newSpeed;
		}

		public void setNewSpeed(float newSpeed) {
			this.newSpeed = newSpeed;
		}

		public boolean isCanceled() {
			return canceled;
		}

		public void setCanceled(boolean canceled) {
			this.canceled = canceled;
		}
	}
}
