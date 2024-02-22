package com.cmorales.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // Permitir acceso sin autenticación a cualquier endpoint bajo /public
                .anyRequest().authenticated() // Requerir autenticación para cualquier otro request
            )
            //  Configura el servidor para utilizar OAuth2 como mecanismo de autenticación, especificando que se utilizarán tokens JWT (JSON Web Tokens) para la autenticación de los recursos
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); 
        return http.build();
    }

}
