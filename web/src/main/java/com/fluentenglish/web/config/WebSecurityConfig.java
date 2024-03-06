package com.fluentenglish.web.config;

import com.fluentenglish.web.auth.Role;
import com.fluentenglish.web.auth.filter.BearerTokenAuthenticationFilter;
import com.fluentenglish.web.auth.token.jwt.JWTAuthenticationProvider;
import com.fluentenglish.web.auth.token.jwt.JWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    @Order(1)
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
                ).cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,
                                                       JWTProcessor jwtProcessor) throws Exception {
        AuthenticationManager authenticationManager = authManager(http, jwtProcessor);

        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationManager(authenticationManager)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new BearerTokenAuthenticationFilter(authenticationManager), SecurityContextHolderFilter.class);

        http.securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .anyRequest().hasRole(Role.USER.name())
                ).cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
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

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
