package io.github.lounode.extrabotany.common.event.entity.living;

import io.github.lounode.extrabotany.common.event.entity.EntityEventWrapper;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityEventWrapper extends EntityEventWrapper {

    private final LivingEntity livingEntity;

    public LivingEntityEventWrapper(LivingEntity entity)
    {
        super(entity);
        livingEntity = entity;
    }

    @Override
    public LivingEntity getEntity()
    {
        return livingEntity;
    }
}
