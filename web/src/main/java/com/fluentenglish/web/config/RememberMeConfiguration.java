package com.fluentenglish.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rememberme")
@Getter
@Setter
public class RememberMeConfiguration {
    private String rememberMeKey;
    private int rememberMeTokenValiditySeconds;
}
