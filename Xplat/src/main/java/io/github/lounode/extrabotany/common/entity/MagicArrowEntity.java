package io.github.lounode.extrabotany.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.client.fx.WispParticleData;


import java.util.List;


public class MagicArrowEntity extends ThrowableProjectile {
    private static final float ATTACK_BOUND_RADIUS = 2.0f;
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_LIFE = "life";
    private static final String TAG_ROTATION = "rotation";
    private static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(MagicArrowEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(MagicArrowEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(MagicArrowEntity.class,
            EntityDataSerializers.FLOAT);

    public MagicArrowEntity(EntityType<? extends MagicArrowEntity> type, Level world) {
        super(type, world);
    }

    //public MagicArrowEntity(Level world, LivingEntity owner) {
        //super(ExtraBotanyEntityType.MAGIC_ARROW, world);
    //}

    @Override
    protected void defineSynchedData() {
        entityData.define(DAMAGE, 0);
        entityData.define(LIFE, 0);
        entityData.define(ROTATION, 0.0F);
    }

    @Override
    public void tick() {
        Entity thrower = getOwner();
        if (!(thrower instanceof Player player) || !thrower.isAlive()) {
            if (!level().isClientSide) {
                discard();
            }
            return;
        }

        if (!level().isClientSide) {
            AABB attackBound = getAttackBound();
            List<LivingEntity> entitiesToAttack = level().getEntitiesOfClass(LivingEntity.class, attackBound).stream()
                    .filter(e -> e != thrower)
                    .filter(e -> e.hurtTime == 0)
                    .toList();
            entitiesToAttack.forEach(e -> {
                double attribute = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                e.hurt(level().damageSources().playerAttack(player), (float) (getDamage() + attribute * 0.8f));
            });
        }

        super.tick();

        //Botania.proxy.wispFX(posX, posY, posZ, 0.1F, 0.85F, 0.1F, 0.2F, 0F);
        /*
        WispParticleData data = WispParticleData.wisp(0.6F, r, g, b, 1.0F);
        level().addParticle(
                data,
                getX(), getY(), getZ(),  // 粒子位置
                0, -0.2F, 0              // 粒子运动速度 (x,y,z)
        );

         */
    }

    @Override
    protected void onHit(HitResult result) {

    }

    public AABB getAttackBound() {
        return new AABB(
                getX() - ATTACK_BOUND_RADIUS, getY() - ATTACK_BOUND_RADIUS, getZ() - ATTACK_BOUND_RADIUS,
                xOld + ATTACK_BOUND_RADIUS, yOld + ATTACK_BOUND_RADIUS, zOld + ATTACK_BOUND_RADIUS);
    }

    public float getRotation() {
        return entityData.get(ROTATION);
    }

    public void setRotation(float rot) {
        entityData.set(ROTATION, rot);
    }

    public int getLife() {
        return entityData.get(LIFE);
    }

    public void setLife(int delay) {
        entityData.set(LIFE, delay);
    }

    public int getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(int delay) {
        entityData.set(DAMAGE, delay);
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }
}
