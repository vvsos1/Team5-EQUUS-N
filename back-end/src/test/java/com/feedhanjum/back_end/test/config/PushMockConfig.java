package com.feedhanjum.back_end.test.config;

import nl.martijndwars.webpush.PushAsyncService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class PushMockConfig {

    @Bean
    public PushAsyncService pushAsyncService() {
        return mock();
    }
}
