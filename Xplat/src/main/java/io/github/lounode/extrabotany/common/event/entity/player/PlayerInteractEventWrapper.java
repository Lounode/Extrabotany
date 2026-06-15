package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class PlayerInteractEventWrapper {
	private final Player entity;
	private final InteractionHand hand;
	private final BlockPos pos;
	private boolean canceled;

	protected PlayerInteractEventWrapper(Player entity, InteractionHand hand, BlockPos pos) {
		this.entity = entity;
		this.hand = hand;
		this.pos = pos;
	}

	public Player getEntity() {
		return entity;
	}

	public InteractionHand getHand() {
		return hand;
	}

	public ItemStack getItemStack() {
		return entity.getItemInHand(hand);
	}

	public BlockPos getPos() {
		return pos;
	}

	public Level getLevel() {
		return entity.level();
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public static class LeftClickEmpty extends PlayerInteractEventWrapper {
		public LeftClickEmpty(Player entity) {
			super(entity, InteractionHand.MAIN_HAND, entity.blockPosition());
		}
	}

	public static class RightClickBlock extends PlayerInteractEventWrapper {
		public RightClickBlock(Player entity, InteractionHand hand, BlockPos pos) {
			super(entity, hand, pos);
		}
	}
}
