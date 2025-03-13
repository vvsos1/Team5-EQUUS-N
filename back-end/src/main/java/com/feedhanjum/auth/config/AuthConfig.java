package com.feedhanjum.auth.config;

import com.feedhanjum.auth.passwordencoder.BCryptPasswordEncoder;
import com.feedhanjum.auth.passwordencoder.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
