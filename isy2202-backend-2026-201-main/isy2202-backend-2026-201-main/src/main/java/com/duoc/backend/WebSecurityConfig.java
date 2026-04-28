package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

   
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers
                .frameOptions(frame -> frame.deny())
                .contentTypeOptions(Customizer.withDefaults())
                .addHeaderWriter(new StaticHeadersWriter(
                    "Content-Security-Policy",
                    "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none';"
                ))
            )
            .authorizeHttpRequests(authz -> authz                
                //.anyRequest().permitAll());
                .requestMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, Constants.LOGIN_URL).permitAll()
                .anyRequest().authenticated());
                //.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }
    @Bean
    public FilterRegistrationBean<JWTAuthorizationFilter> jwtFilterRegistration(JWTAuthorizationFilter filter) {
    FilterRegistrationBean<JWTAuthorizationFilter> registration = new FilterRegistrationBean<>(filter);
    registration.setEnabled(false);
    return registration;
    }
}