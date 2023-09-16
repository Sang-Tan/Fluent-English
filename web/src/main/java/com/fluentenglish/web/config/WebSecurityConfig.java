package com.fluentenglish.web.config;

import com.fluentenglish.web.admin.authen.security.AdminAuthenticationManager;
import com.fluentenglish.web.admin.authen.security.Role;
import com.fluentenglish.web.user.authen.security.UserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private AdminAuthenticationManager adminAuthenticationManager;
    @Autowired
    private UserAuthenticationManager userAuthenticationManager;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.GET,
                  "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http

                .securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/admin/login")
                        .loginPage("/admin/login") // Set the login page for admins
                        .defaultSuccessUrl("/admin")
                        .failureUrl("/admin/login?badCredentials=true")
                        .permitAll()
                )
                .authenticationManager(adminAuthenticationManager.authManager(http))
                .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/admin/login")
                        .maximumSessions(1)
                        .sessionRegistry(new SessionRegistryImpl()))
                .logout((logout) -> logout.logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .securityMatcher("/user/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasRole(Role.USER.name())
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/user/login")
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/user")
                        .failureUrl("/user/login?badCredentials=true")
                        .permitAll()
                )
                .authenticationManager(userAuthenticationManager.authManager(http))
                .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/user/login")
                        .maximumSessions(1)
                        .sessionRegistry(new SessionRegistryImpl()))
                .logout((logout) -> logout.logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }




}
