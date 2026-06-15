package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDamageEventWrapper extends LivingAttackEventWrapper {
	public LivingDamageEventWrapper(LivingEntity entity, DamageSource source, float amount) {
		super(entity, source, amount);
	}
}
