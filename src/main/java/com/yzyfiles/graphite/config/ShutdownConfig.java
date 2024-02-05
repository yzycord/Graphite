package com.yzyfiles.graphite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class ShutdownConfig {

    @Bean
    public AtomicBoolean shutdownFlag() {
        return new AtomicBoolean(false);
    }
}