package io.github.lounode.extrabotany.common.event.entity;

import io.github.lounode.extrabotany.common.event.api.EventWrapper;
import net.minecraft.world.entity.Entity;

public class EntityEventWrapper extends EventWrapper {
    private final Entity entity;

    public EntityEventWrapper(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
