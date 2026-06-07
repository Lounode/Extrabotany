package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class AttackEntityEventWrapper {
	private final Player entity;
	private final Entity target;
	private boolean canceled;

	public AttackEntityEventWrapper(Player entity, Entity target) {
		this.entity = entity;
		this.target = target;
	}

	public Player getEntity() {
		return entity;
	}

	public Entity getTarget() {
		return target;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
}
