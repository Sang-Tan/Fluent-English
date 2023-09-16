package com.fluentenglish.web.admin.authen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("adminAuthenticationProvider")
public class AdminAuthenticationProvider {
    @Autowired
    private AdminDetailsService adminDetailsService;
    public DaoAuthenticationProvider getAuthProvider() {
        final DaoAuthenticationProvider customAuthProvider = new DaoAuthenticationProvider();
        customAuthProvider.setUserDetailsService(adminDetailsService);
        customAuthProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return customAuthProvider;
    }
}
