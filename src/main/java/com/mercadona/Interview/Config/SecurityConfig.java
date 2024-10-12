package com.mercadona.Interview.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/h2-console/**", "/api/products/**").permitAll() // Permitir acceso público a /h2-console y /api/products
                    .anyRequest().authenticated() // Requiere autenticación para otros endpoints
            )
            .headers(headers -> headers
                    .frameOptions().sameOrigin() // This is deprecated (iframes) but necessary if you want to view H2 console.
            );

    return http.build();
  }
}
