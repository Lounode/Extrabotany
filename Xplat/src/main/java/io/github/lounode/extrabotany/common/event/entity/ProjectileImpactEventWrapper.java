package io.github.lounode.extrabotany.common.event.entity;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProjectileImpactEventWrapper extends EntityEventWrapper {

    private final HitResult ray;
    private final Projectile projectile;

    private ImpactResult result = ImpactResult.DEFAULT;

    public ProjectileImpactEventWrapper(Projectile projectile, HitResult ray)
    {
        super(projectile);
        this.ray = ray;
        this.projectile = projectile;
    }

    public HitResult getRayTraceResult()
    {
        return ray;
    }

    public Projectile getProjectile()
    {
        return projectile;
    }

    public void setImpactResult(@NotNull ImpactResult newResult)
    {
        this.result = Objects.requireNonNull(newResult, "ImpactResult cannot be null");
    }

    public ImpactResult getImpactResult()
    {
        return result;
    }


    public enum ImpactResult
    {
        /**
         * The default behaviour, the projectile will be destroyed and the hit will be processed.
         */
        DEFAULT,
        /**
         * The projectile will pass through the current entity as if it wasn't there. This will return default behaviour if there is no entity.
         */
        SKIP_ENTITY,
        /**
         * Damage the entity and stop the projectile here, the projectile will not pierce.
         */
        STOP_AT_CURRENT,
        /**
         * Cancel the piercing aspect of the projectile, and do not damage the entity.
         */
        STOP_AT_CURRENT_NO_DAMAGE
    }
}
