package io.github.lounode.extrabotany.common.event;

import io.github.lounode.extrabotany.common.event.api.EventWrapper;
import io.github.lounode.extrabotany.common.event.api.IPlatformEventHelper;
import io.github.lounode.extrabotany.common.event.api.SubscribeEventWrapper;

import java.lang.reflect.Method;
import java.util.Arrays;

public class EventFactory {
    public static void register(Object target) {
        IPlatformEventHelper helper = IPlatformEventHelper.INSTANCE;

        Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(SubscribeEventWrapper.class))
                .forEach(m -> registerMethod(helper, target, m));
    }

    private static void registerMethod(IPlatformEventHelper helper, Object target, Method method) {
        Class<?>[] params = method.getParameterTypes();
        if (params.length != 1 || !EventWrapper.class.isAssignableFrom(params[0])) {
            throw new IllegalArgumentException("Event subscriber can only subscribe one event!");
        }

        helper.registerListener(target, method, params[0]);
    }
}
