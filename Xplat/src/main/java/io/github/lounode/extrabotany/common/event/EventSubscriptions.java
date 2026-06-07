package io.github.lounode.extrabotany.common.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public final class EventSubscriptions {
	private static final Set<Object> LISTENERS = Collections.newSetFromMap(new WeakHashMap<>());

	private EventSubscriptions() {}

	public static void register(Object listener) {
		synchronized (LISTENERS) {
			LISTENERS.add(listener);
		}
	}

	public static void unregister(Object listener) {
		synchronized (LISTENERS) {
			LISTENERS.remove(listener);
		}
	}

	public static <T> List<T> listeners(Class<T> type) {
		List<T> result = new ArrayList<>();
		synchronized (LISTENERS) {
			for (Object listener : LISTENERS) {
				if (type.isInstance(listener)) {
					result.add(type.cast(listener));
				}
			}
		}
		return result;
	}
}
