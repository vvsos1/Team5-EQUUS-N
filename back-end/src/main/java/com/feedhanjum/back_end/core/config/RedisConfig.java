package com.feedhanjum.back_end.core.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.embedded.RedisServer;

import java.io.IOException;

@Slf4j
@Profile({"dev", "prod"})
@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JdkSerializationRedisSerializer();

    }


    @Configuration
    @Profile(("dev"))
    static class EmbeddedRedisConfig {
        private final RedisServer redisServer;

        public EmbeddedRedisConfig(@Value("${spring.data.redis.port}") int port) throws IOException {
            log.info("Embedded redis server is ready for port {}", port);
            this.redisServer = new RedisServer(port);
        }

        @PostConstruct
        public void postConstruct() throws IOException {
            log.info("Embedded redis server starting");
            redisServer.start();
        }

        @PreDestroy
        public void preDestroy() throws IOException {
            log.info("Embedded redis server stopping");
            redisServer.stop();
        }
    }
}
