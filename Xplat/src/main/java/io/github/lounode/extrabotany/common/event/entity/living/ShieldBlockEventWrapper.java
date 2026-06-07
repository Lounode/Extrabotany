package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ShieldBlockEventWrapper {
	private final LivingEntity entity;
	private final DamageSource damageSource;
	private final float blockedDamage;

	public ShieldBlockEventWrapper(LivingEntity entity, DamageSource damageSource, float blockedDamage) {
		this.entity = entity;
		this.damageSource = damageSource;
		this.blockedDamage = blockedDamage;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public DamageSource getDamageSource() {
		return damageSource;
	}

	public float getBlockedDamage() {
		return blockedDamage;
	}
}
