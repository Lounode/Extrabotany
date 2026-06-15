package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.entity.LivingEntity;

public class LivingHealEventWrapper {
	private final LivingEntity entity;
	private float amount;

	public LivingHealEventWrapper(LivingEntity entity, float amount) {
		this.entity = entity;
		this.amount = amount;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
