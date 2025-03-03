package com.feedhanjum.back_end.core.config;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.core.event.Events;
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
