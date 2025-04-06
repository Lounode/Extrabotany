package io.github.lounode.extrabotany.common;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanyDamageTypes {
    public static final ResourceKey<DamageType> LINK_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, prefix("link"));
    public static final ResourceKey<DamageType> EXCALIBUR_BEAM_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, prefix("excalibur"));
    public static final ResourceKey<DamageType> JINGWEI_PUNCH_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, prefix("jingwei"));

    public static final DamageType LINK = new DamageType("extrabotany.link", 0.1f);
    public static final DamageType EXCALIBUR = new DamageType("extrabotany.excalibur", 0.1f);
    public static final DamageType JINGWEI = new DamageType("extrabotany.jingwei", 0.1f);



    public static class Sources {

        private static Holder.Reference<DamageType> getHolder(RegistryAccess ra, ResourceKey<DamageType> key) {
            return ra.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
        }

        public static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey) {
            return new DamageSource(getHolder(ra, resourceKey));
        }

        public static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity) {
            return new DamageSource(getHolder(ra, resourceKey), entity);
        }

        public static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity, @Nullable Entity entity2) {
            return new DamageSource(getHolder(ra, resourceKey), entity, entity2);
        }

        public static DamageSource linkDamage(RegistryAccess ra) {
            return source(ra, LINK_DAMAGE);
        }

        public static DamageSource excaliburDamage(RegistryAccess ra, Entity source) {
            return source(ra, EXCALIBUR_BEAM_DAMAGE, source);
        }

        public static DamageSource jingweiDamage(RegistryAccess ra, @Nullable Entity source) {
            return source == null ? source(ra, JINGWEI_PUNCH_DAMAGE) : source(ra, JINGWEI_PUNCH_DAMAGE, source);
        }
    }
}
