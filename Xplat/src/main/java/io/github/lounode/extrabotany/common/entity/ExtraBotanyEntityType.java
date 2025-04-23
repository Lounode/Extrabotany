package io.github.lounode.extrabotany.common.entity;

import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import io.github.lounode.extrabotany.common.entity.gaia.GaiaIII;
import io.github.lounode.extrabotany.common.lib.LibEntityNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;

public class ExtraBotanyEntityType {
    public static final EntityType<AuraFireEntity> AURA_FIRE = EntityType.Builder.<AuraFireEntity>of(
            AuraFireEntity::new, MobCategory.MISC)
            .sized(0, 0)
            .noSummon()
            .updateInterval(10)
            .clientTrackingRange(10)
            .build(LibEntityNames.AURA_FIRE.toString());

    public static final EntityType<Gaia> GAIA_LEGACY = EntityType.Builder.<Gaia>of(
                    Gaia::new, MobCategory.MONSTER)
            .sized(0.6F, 1.8F)
            .fireImmune()
            .clientTrackingRange(10)
            .updateInterval(10)
            .build(LibEntityNames.GAIA_LEGACY.toString());

    public static final EntityType<MagicLandMineEntity> MAGIC_LANDMINE = EntityType.Builder.<MagicLandMineEntity>of(
            MagicLandMineEntity::new, MobCategory.MISC)
            .sized(5F, 0.1F)
            .clientTrackingRange(8)
            .updateInterval(40)
            .build(LibEntityNames.MAGIC_LANDMINE.toString());

    public static final EntityType<GaiaIII> GAIA_III = EntityType.Builder.<GaiaIII>of(
            GaiaIII::new, MobCategory.MONSTER)
            .sized(0.6F, 1.8F)
            .fireImmune()
            .clientTrackingRange(10)
            .updateInterval(10)
            .build(LibEntityNames.GAIA_III.toString());

    public static void registerEntities(BiConsumer<EntityType<?>, ResourceLocation> r) {
        r.accept(AURA_FIRE, LibEntityNames.AURA_FIRE);
        r.accept(MAGIC_LANDMINE, LibEntityNames.MAGIC_LANDMINE);
        r.accept(GAIA_LEGACY, LibEntityNames.GAIA_LEGACY);
        r.accept(GAIA_III, LibEntityNames.GAIA_III);
    }

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> consumer) {
        consumer.accept(GAIA_LEGACY, Gaia.createGaiaAttributes());
        consumer.accept(GAIA_III, GaiaIII.createGaiaAttributes());

    }
}
