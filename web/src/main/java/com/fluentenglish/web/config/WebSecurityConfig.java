package com.fluentenglish.web.config;

import com.fluentenglish.web.admin.authen.security.AdminDetailsService;
import com.fluentenglish.web.admin.authen.security.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AdminDetailsService adminDetailsService;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/resources/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityContext((securityContext) -> securityContext.requireExplicitSave(true))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin")
                        .failureUrl("/admin/login?badCredentials=true")
                        .loginPage("/admin/login")
                        .permitAll()
                )
                .authenticationManager(authManager(http))
                .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/admin/login")
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry()))
                .logout((logout) -> logout.logoutUrl("/admin/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/admin/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider customAuthProvider = new DaoAuthenticationProvider();
        customAuthProvider.setUserDetailsService(adminDetailsService);
        customAuthProvider.setPasswordEncoder(passwordEncoder());
        return customAuthProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
