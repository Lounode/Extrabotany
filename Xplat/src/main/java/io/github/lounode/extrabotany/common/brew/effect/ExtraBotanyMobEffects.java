package io.github.lounode.extrabotany.common.brew.effect;

import io.github.lounode.extrabotany.common.lib.LibPotionNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanyMobEffects {
    public static final MobEffect IMMOBILIZE = new ImmobilizeMobEffect(MobEffectCategory.HARMFUL, 9154528)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-1.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);;
    public static final MobEffect LINK = new LinkMobEffect(MobEffectCategory.HARMFUL, 9154528);
    public static final MobEffect HEAL_REVERSE = new HealReverseMobEffect(MobEffectCategory.HARMFUL, 0X4B0082);

    public static void registerPotions(BiConsumer<MobEffect, ResourceLocation> r) {
        r.accept(IMMOBILIZE, prefix(LibPotionNames.IMMOBILIZE));
        r.accept(LINK, prefix(LibPotionNames.LINK));
        r.accept(HEAL_REVERSE, prefix(LibPotionNames.HEAL_REVERSE));
    }
}
