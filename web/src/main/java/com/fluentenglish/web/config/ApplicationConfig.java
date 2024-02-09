package com.fluentenglish.web.config;

import com.fluentenglish.web.spacedrepetition.fsrs.FSRSConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public FSRSConfiguration fsrsConfiguration() {
        return new FSRSConfiguration();
    }
}
