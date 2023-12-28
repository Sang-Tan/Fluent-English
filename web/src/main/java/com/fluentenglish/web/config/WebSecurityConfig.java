package com.fluentenglish.web.config;

import com.fluentenglish.web.auth.admin.AdminDetailsService;
import com.fluentenglish.web.auth.Role;
import com.fluentenglish.web.auth.filter.BearerTokenAuthenticationFilter;
import com.fluentenglish.web.auth.token.jwt.JWTAuthenticationProvider;
import com.fluentenglish.web.auth.token.jwt.JWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.GET,
                  "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http,
                                                        JWTProcessor jwtProcessor) throws Exception {
        AuthenticationManager authenticationManager = authManager(http, jwtProcessor);

        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationManager(authenticationManager)
                        .sessionManagement((sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterAfter(new BearerTokenAuthenticationFilter(authenticationManager), SecurityContextHolderFilter.class);

        http.securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/api/login").permitAll()
                        .anyRequest().hasRole(Role.ADMIN.name())
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private AuthenticationManager authManager(HttpSecurity http, JWTProcessor jwtProcessor) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(jwtAuthProvider(jwtProcessor))
                .build();
    }

    private AuthenticationProvider jwtAuthProvider(JWTProcessor jwtProcessor) {
        return new JWTAuthenticationProvider(jwtProcessor);
    }
}
