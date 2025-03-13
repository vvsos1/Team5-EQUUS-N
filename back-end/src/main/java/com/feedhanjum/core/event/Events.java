package com.feedhanjum.core.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Events {

    private static EventPublisher publisher;

    public static void raise(Object event) {
        if (publisher == null) {
            log.warn("Event publisher not set. event: {}", event);
        }
        publisher.publishEvent(event);
    }

    public static void setPublisher(EventPublisher publisher) {
        Events.publisher = publisher;
    }
}
