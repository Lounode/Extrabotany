package io.github.lounode.extrabotany.common.event.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marker annotation indicating the event type is able to be cancelled using {@link EventWrapper#setCanceled(boolean)}
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
public @interface Cancelable{}
