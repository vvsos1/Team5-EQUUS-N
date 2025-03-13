package com.feedhanjum.core.config;

import com.feedhanjum.core.event.EventPublisher;
import com.feedhanjum.core.event.Events;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class EventConfig {
    @Bean
    public InitializingBean eventsInitializer(EventPublisher eventPublisher) {
        return () -> Events.setPublisher(eventPublisher);
    }
}
