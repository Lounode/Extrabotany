package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingAttackEventWrapper {
	private final LivingEntity entity;
	private final DamageSource source;
	private float amount;
	private boolean canceled;

	public LivingAttackEventWrapper(LivingEntity entity, DamageSource source, float amount) {
		this.entity = entity;
		this.source = source;
		this.amount = amount;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public DamageSource getSource() {
		return source;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
}
