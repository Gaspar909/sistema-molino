package com.molinosystem.sistema_molino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Este es el Bean que te estaba pidiendo tu UserService
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Al usar Spring Boot 4 / Spring Security 6+, necesitas definir este filtro
    // para permitir el acceso a tus endpoints (puedes ajustarlo después)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivamos CSRF para pruebas en Postman
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas las peticiones de momento
            );
        
        return http.build();
    }
}