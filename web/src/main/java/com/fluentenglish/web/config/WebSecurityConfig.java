package com.fluentenglish.web.config;

import com.fluentenglish.web.admin.authen.security.AdminDetailsService;
import com.fluentenglish.web.admin.authen.security.Role;
import com.fluentenglish.web.user.authen.security.UserDetailsServiceImpl;
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
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.GET,
                  "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http, @Autowired AdminDetailsService adminDetailsService) throws Exception {
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
                .authenticationManager(getUserAuthManager(adminDetailsService, http))
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
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,  @Autowired UserDetailsServiceImpl userDetailsService) throws Exception {
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
                .authenticationManager(getUserAuthManager(userDetailsService, http))
                .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/user/login")
                        .maximumSessions(1)
                        .sessionRegistry(new SessionRegistryImpl()))
                .logout((logout) -> logout.logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login?logoutSuccess=true")
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
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}
