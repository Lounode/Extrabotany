package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.entity.LivingEntity;

public class LivingHealEventWrapper extends LivingEntityEventWrapper {
    private float amount;

    public LivingHealEventWrapper(LivingEntity livingEntity, float amount) {
        super(livingEntity);
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
