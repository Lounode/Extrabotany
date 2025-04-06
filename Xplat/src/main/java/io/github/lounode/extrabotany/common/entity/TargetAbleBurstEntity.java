package io.github.lounode.extrabotany.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.LensEffectItem;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.entity.ManaBurstEntity;
import vazkii.botania.common.item.equipment.bauble.ManaseerMonocleItem;
import vazkii.botania.common.proxy.Proxy;
import vazkii.botania.xplat.BotaniaConfig;

import java.util.Random;
import java.util.UUID;

public class TargetAbleBurstEntity extends ManaBurstEntity {
    private static final String TAG_TARGET = "targetUUID";

    private UUID targetUUID = null;
    public TargetAbleBurstEntity(Player player) {
        super(player);
    }

    @Override
    public void tick() {
        super.tick();
        this.particles();
    }

    @Override
    public void particles() {
        if (!isAlive() || !level().isClientSide) {
            return;
        }

        LensEffectItem lens = getLensInstance();
        if (lens != null && !lens.doParticles(this, getSourceLens())) {
            return;
        }

        int color = getColor();
        float r = (color >> 16 & 0xFF) / 255F;
        float g = (color >> 8 & 0xFF) / 255F;
        float b = (color & 0xFF) / 255F;
        float osize = getParticleSize();
        float size = osize;


        Player player = Proxy.INSTANCE.getClientPlayer();
        boolean depth = player == null || !ManaseerMonocleItem.hasMonocle(player);

        if (!BotaniaConfig.client().subtlePowerSystem()) {
            WispParticleData data = WispParticleData.wisp(0.1F * size, r, g, b, depth);
            Proxy.INSTANCE.addParticleForceNear(level(), data, getX(), getY(), getZ(), (float) (Math.random() - 0.5F) * 0.02F, (float) (Math.random() - 0.5F) * 0.02F, (float) (Math.random() - 0.5F) * 0.01F);
        } else {
            float or = r;
            float og = g;
            float ob = b;

            double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b; // Standard relative luminance calculation

            double iterX = getX();
            double iterY = getY();
            double iterZ = getZ();

            Vec3 currentPos = position();
            Vec3 oldPos = new Vec3(xo, yo, zo);
            Vec3 diffVec = oldPos.subtract(currentPos);
            Vec3 diffVecNorm = diffVec.normalize();

            double distance = 0.095;

            do {
                if (luminance < 0.1) {
                    r = or + (float) Math.random() * 0.125F;
                    g = og + (float) Math.random() * 0.125F;
                    b = ob + (float) Math.random() * 0.125F;
                }
                size = osize + ((float) Math.random() - 0.5F) * 0.065F + (float) Math.sin(new Random(uuid.getMostSignificantBits()).nextInt(9001)) * 0.4F;
                WispParticleData data = WispParticleData.wisp(0.2F * size, r, g, b, depth);
                Proxy.INSTANCE.addParticleForceNear(level(), data, iterX, iterY, iterZ,
                        (float) -getDeltaMovement().x() * 0.01F,
                        (float) -getDeltaMovement().y() * 0.01F,
                        (float) -getDeltaMovement().z() * 0.01F);

                iterX += diffVecNorm.x * distance;
                iterY += diffVecNorm.y * distance;
                iterZ += diffVecNorm.z * distance;

                currentPos = new Vec3(iterX, iterY, iterZ);
                diffVec = oldPos.subtract(currentPos);
                if (getOrbitTime() > 0) {
                    break;
                }
            } while (Math.abs(diffVec.length()) > distance);

            WispParticleData data = WispParticleData.wisp(0.1F * size, or, og, ob, depth);
            level().addParticle(data, iterX, iterY, iterZ, (float) (Math.random() - 0.5F) * 0.06F, (float) (Math.random() - 0.5F) * 0.06F, (float) (Math.random() - 0.5F) * 0.06F);
        }

    }

    private LensEffectItem getLensInstance() {
        ItemStack lens = getSourceLens();
        if (!lens.isEmpty() && lens.getItem() instanceof LensEffectItem effect) {
            return effect;
        }

        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        UUID identity = getTargetUUID();
        boolean hasTarget = identity != null;
        if (hasTarget) {
            tag.putUUID(TAG_TARGET, identity);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);

        boolean hasTarget = cmp.contains(TAG_TARGET);
        if (hasTarget) {
            UUID serializedUuid = cmp.getUUID(TAG_TARGET);
            setTargetUUID(serializedUuid);
        }
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public void setTargetUUID(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }
}
