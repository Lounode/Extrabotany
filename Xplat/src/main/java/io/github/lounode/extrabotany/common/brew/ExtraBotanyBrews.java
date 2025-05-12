package io.github.lounode.extrabotany.common.brew;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionUtils;
import vazkii.botania.api.brew.Brew;
import io.github.lounode.extrabotany.common.lib.LibBrewNames;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanyBrews {

    public static Brew manaCocktail = new Brew(0x59B7FF, 25000,
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60),
            new MobEffectInstance(MobEffects.JUMP, 20 * 60),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60)
    ).setNotBloodPendantInfusable().setNotIncenseInfusable();

    public static void submitRegistrations(BiConsumer<Brew, ResourceLocation> r) {
        r.accept(manaCocktail, prefix(LibBrewNames.MANA_COCKTAIL));
    }

    private static Brew make(int cost, MobEffectInstance... effects) {
        return new Brew(PotionUtils.getColor(Arrays.asList(effects)), cost, effects);
    }
}
