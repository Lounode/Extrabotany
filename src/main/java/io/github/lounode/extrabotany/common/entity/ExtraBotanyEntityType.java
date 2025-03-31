package io.github.lounode.extrabotany.common.entity;

import io.github.lounode.extrabotany.common.lib.LibEntityNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;

public class ExtraBotanyEntityType {
    public static final EntityType<MagicArrowEntity> MAGIC_ARROW = EntityType.Builder.<MagicArrowEntity>of(
            MagicArrowEntity::new, MobCategory.MISC)
            .sized(0, 0)
            .updateInterval(10)
            .clientTrackingRange(10)
            .build(LibEntityNames.MAGIC_ARROW.toString());

    public static void registerEntities(BiConsumer<EntityType<?>, ResourceLocation> r) {
        r.accept(MAGIC_ARROW, LibEntityNames.MAGIC_ARROW);
    }

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> consumer) {

    }
}
