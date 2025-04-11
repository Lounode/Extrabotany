package io.github.lounode.extrabotany.common.event.api;

import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import vazkii.botania.api.ServiceUtil;

import java.lang.reflect.Method;

public interface IPlatformEventHelper {
    IPlatformEventHelper INSTANCE = ServiceUtil.findService(IPlatformEventHelper.class, null);
    void registerListener(Object target, Method method, Class<?> eventType);
}
