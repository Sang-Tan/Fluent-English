package com.fluentenglish.web.config;

import com.fluentenglish.web.auth.admin.AdminDetailsService;
import com.fluentenglish.web.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.GET,
                  "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http, @Autowired AdminDetailsService adminDetailsService
            , @Autowired RememberMeConfiguration rememberMeConfiguration ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/admin/login")
                        .loginPage("/admin/login")
                        .defaultSuccessUrl("/admin")
                        .failureUrl("/admin/login?badCredentials=true")
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe
                        .tokenValiditySeconds(rememberMeConfiguration.getRememberMeTokenValiditySeconds())
                        .key(rememberMeConfiguration.getRememberMeKey())
                        .rememberMeServices(new TokenBasedRememberMeServices(rememberMeConfiguration.getRememberMeKey(), adminDetailsService))
                )
                .authenticationManager(getUserAuthManager(adminDetailsService, http))
                .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/admin/login"))
                .logout((logout) -> logout.logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
    private  DaoAuthenticationProvider getAuthProvider(UserDetailsService userDetailsService) {
        final DaoAuthenticationProvider customAuthProvider = new DaoAuthenticationProvider();
        customAuthProvider.setUserDetailsService(userDetailsService);
        customAuthProvider.setPasswordEncoder(getPasswordEncoder());
        return customAuthProvider;
    }
    private AuthenticationManager getUserAuthManager(UserDetailsService userDetailsService, HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(getAuthProvider(userDetailsService))
                .build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
