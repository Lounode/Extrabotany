package io.github.lounode.extrabotany.fabric.event;

import io.github.lounode.extrabotany.common.event.entity.living.LivingHealEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.MobEffectEventWrapper;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class LivingEntityEvents {
    public static final Event<LivingEntityEvents.Heal> HEAL = EventFactory.createArrayBacked(LivingEntityEvents.Heal.class,
            (listeners) -> (event) -> {
                for(var callback : listeners) {
                    callback.onHeal(event);
                    if (event.isCanceled()) {
                        return;
                    }
                }
    });

    public static final Event<LivingEntityEvents.MobEffect.Add> MOB_EFFECT_ADD = EventFactory.createArrayBacked(LivingEntityEvents.MobEffect.Add.class,
            (listeners) -> (event) -> {
                for (var callback : listeners) {
                    callback.onMobEffectAdd(event);
                }
    });

    @FunctionalInterface
    public interface Heal {
        void onHeal(LivingHealEventWrapper event);
    }


    public interface MobEffect {
        @FunctionalInterface
        public interface Add {
            void onMobEffectAdd(MobEffectEventWrapper.Added event);
        }

    }
}
