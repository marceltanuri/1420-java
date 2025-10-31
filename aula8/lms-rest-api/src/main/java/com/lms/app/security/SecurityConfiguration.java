package com.lms.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração principal do Spring Security.
 */
@Configuration
@EnableWebSecurity
// Habilita a segurança baseada em anotações (@PreAuthorize) nos métodos
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    // 1. Define o PasswordEncoder: fundamental para criptografar senhas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa BCrypt, o padrão recomendado pelo Spring Security
        return new BCryptPasswordEncoder();
    }

    // 2. Define a cadeia de filtros de segurança (SecurityFilterChain).
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 2.1. Configura a autorização de requisições HTTP
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            // 2.2. Habilita Basic Authentication
            .httpBasic(Customizer.withDefaults())
            
            // 2.3. Desabilita CSRF (Comum para APIs REST stateless)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2.4. Permite que o H2 Console rode dentro de um frame (iframe)
            .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable());

        return http.build();
    }
}
