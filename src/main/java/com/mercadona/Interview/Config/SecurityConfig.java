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
        .csrf(AbstractHttpConfigurer::disable)  // Deshabilitar CSRF
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/products/**").permitAll()  // Permitir acceso público a /api/productos
            .anyRequest().authenticated()  // Requiere autenticación para otros endpoints
        );

    return http.build();
  }
}
