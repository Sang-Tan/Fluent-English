package com.fluentenglish.web.user.authen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userAuthenticationProvider")
public class UserAuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    public DaoAuthenticationProvider getAuthProvider() {
        final DaoAuthenticationProvider customAuthProvider = new DaoAuthenticationProvider();
        customAuthProvider.setUserDetailsService(userDetailsService);
        customAuthProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return customAuthProvider;
    }
}
