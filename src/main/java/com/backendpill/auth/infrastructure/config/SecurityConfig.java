package com.backendpill.auth.infrastructure.config;

import com.backendpill.auth.infrastructure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración principal de la cadena de filtros de seguridad (Security Filter Chain).
 * <p>
 * Define las reglas de autorización HTTP, la gestión de sesiones (Stateless para JWT)
 * y la integración del filtro personalizado de autenticación.
 * La anotación {@code @EnableMethodSecurity} habilita el control de acceso a nivel de método
 * (ej. @PreAuthorize).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * <p>
     * Estrategia de seguridad:
     * 1. CSRF deshabilitado (no es necesario para APIs REST stateless).
     * 2. Lista blanca para rutas de autenticación y documentación (Swagger).
     * 3. Todas las demás peticiones requieren autenticación.
     * 4. Gestión de sesiones sin estado (Stateless), delegando el estado al token JWT.
     * 5. Inserción del filtro JWT antes del filtro estándar de usuario/contraseña.
     *
     * @param http Constructor de seguridad HTTP.
     * @return La cadena de filtros construida.
     * @throws Exception Si hay errores en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}