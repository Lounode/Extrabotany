package io.github.lounode.extrabotany.common.event.api;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener
{
    void invoke(EventWrapper event);

    default String listenerName() {
        return getClass().getName();
    }
}
