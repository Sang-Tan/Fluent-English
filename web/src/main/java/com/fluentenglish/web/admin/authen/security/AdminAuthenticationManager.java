package com.fluentenglish.web.admin.authen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component("adminAuthenticationManager")
public class AdminAuthenticationManager {
    @Autowired
    AdminAuthenticationProvider adminAuthenticationProvider;
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(adminAuthenticationProvider.getAuthProvider())
                .build();
    }
}
