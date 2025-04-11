package io.github.lounode.extrabotany.forge.xplat;


import io.github.lounode.extrabotany.common.event.api.EventWrapper;
import io.github.lounode.extrabotany.common.event.api.IPlatformEventHelper;
import io.github.lounode.extrabotany.common.event.entity.ProjectileImpactEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.MobEffectEventWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ForgeEventHelper implements IPlatformEventHelper {

    private static final Map<Class<? extends EventWrapper>, Function<Event, EventWrapper>> FORGE_TO_WRAPPER = new HashMap<>();
    private static final Map<Class<? extends EventWrapper>, Class<? extends Event>> WRAPPER_TO_FORGE = new HashMap<>();
    static {

        registerConverter(MobEffectEventWrapper.Added.class, MobEffectEvent.Added.class,
                forgeEvent -> {
                    MobEffectEvent.Added event = (MobEffectEvent.Added) forgeEvent;
                    return new MobEffectEventWrapper.Added(
                            event.getEntity(), event.getOldEffectInstance(),
                            event.getEffectInstance(), event.getEffectSource()
                    );
                });

        /*
        registerConverter(ProjectileImpactEventWrapper.class,
                forgeEvent -> new ProjectileImpactEventWrapper((net.minecraftforge.event.entity.ProjectileImpactEvent) forgeEvent),
                ProjectileImpactEvent.class);

         */
    }



    private static <T extends EventWrapper> void registerConverter(
            Class<T> wrapperClass,
            Class<? extends Event> forgeEventClass,
            Function<Event, T> converter
            ) {
        FORGE_TO_WRAPPER.put(wrapperClass, converter::apply);
        WRAPPER_TO_FORGE.put(wrapperClass, forgeEventClass);
    }

    private Class<? extends Event> getForgeEventClass(Class<? extends EventWrapper> wrapperClass) {
        Class<? extends Event> forgeEventClass = WRAPPER_TO_FORGE.get(wrapperClass);
        if (forgeEventClass == null) {
            throw new IllegalArgumentException("UnSupport Wrapper: " + wrapperClass.getName());
        }
        return forgeEventClass;
    }


    @Override
    public void registerListener(Object target, Method method, Class<?> eventType) {
        if (!EventWrapper.class.isAssignableFrom(eventType)) {
            throw new IllegalArgumentException("EventType must extend EventWrapper");
        }

        Class<? extends Event> forgeEventClass = getForgeEventClass((Class<? extends EventWrapper>) eventType);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, forgeEventClass, event -> {
            try {
                EventWrapper wrapperEvent = FORGE_TO_WRAPPER.get(eventType).apply(event);
                method.invoke(target, wrapperEvent);
            } catch (Exception e) {
                throw new RuntimeException("Event call Error!", e);
            }
        });
    }





}
