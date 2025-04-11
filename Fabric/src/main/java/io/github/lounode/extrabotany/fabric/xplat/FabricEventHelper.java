package io.github.lounode.extrabotany.fabric.xplat;

import io.github.lounode.extrabotany.common.event.api.EventWrapper;
import io.github.lounode.extrabotany.common.event.api.IPlatformEventHelper;
import io.github.lounode.extrabotany.common.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.extrabotany.fabric.event.LivingEntityEvents;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FabricEventHelper implements IPlatformEventHelper {
    private static final Map<Class<? extends EventWrapper>, Consumer<Object>> FABRIC_LISTENERS = new HashMap<>();

    static {
        registerFabricListener(MobEffectEventWrapper.Added.class, callback -> {
            LivingEntityEvents.MOB_EFFECT_ADD.register(callback::accept);
        });

    }

    private static <T extends EventWrapper> void registerFabricListener(
            Class<T> wrapperClass,
            Consumer<Consumer<T>> registrar) {
        // 显式转换为 Consumer<Object>
        FABRIC_LISTENERS.put(wrapperClass, (Consumer<Object>) obj -> {
            @SuppressWarnings("unchecked")
            Consumer<T> typedCallback = (Consumer<T>) obj;
            registrar.accept(typedCallback);
        });
    }

    @Override
    public void registerListener(Object target, Method method, Class<?> eventType) {
        if (!EventWrapper.class.isAssignableFrom(eventType)) {
            throw new IllegalArgumentException("EventType must extend EventWrapper");
        }

        Consumer<Object> listener = FABRIC_LISTENERS.get(eventType);
        if (listener != null) {
            // 创建类型安全的消费者
            Consumer<Object> rawConsumer = obj -> {
                try {
                    EventWrapper event = (EventWrapper) obj;
                    method.invoke(target, event);
                } catch (Exception e) {
                    throw new RuntimeException("Event call Error!", e);
                }
            };

            listener.accept(rawConsumer);
        } else {
            throw new UnsupportedOperationException("Unsupported event type: " + eventType.getName());
        }
    }
}
