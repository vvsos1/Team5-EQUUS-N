package com.feedhanjum.test.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class AiMockConfiguration {

    @Bean
    public OpenAiChatModel mockOpenAiChatModel() {
        return mock();
    }

}
