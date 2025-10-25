package com.backendpill.auth.infrastructure;

import com.backendpill.auth.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita la seguridad por anotaciones (ej. @PreAuthorize)
@RequiredArgsConstructor
public class SecurityConfig {

    // Necesitaremos el filtro JWT que valida el token en cada petición
    // (Te daré el código de este filtro en el siguiente paso)
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Nuestro UserService que implementa UserDetailsService
    private final UserService userService;

    // BEAN 1: EL FILTRO DE SEGURIDAD PRINCIPAL
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF (común en APIs REST con JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Rutas públicas de auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir Swagger
                        .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // API sin estado (no usa sesiones)
                )
                .authenticationProvider(authenticationProvider()) // Seteamos nuestro proveedor de auth
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añadimos el filtro JWT

        return http.build();
    }

    // BEAN 2: EL BEAN QUE TE FALTABA (AuthenticationManager)
    // Lo exponemos desde la configuración de autenticación de Spring
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // BEAN 3: EL PROVEEDOR DE AUTENTICACIÓN
    // Conecta Spring Security con tu UserService
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // Le dice a Spring que use tu UserService
        authProvider.setPasswordEncoder(passwordEncoder()); // Le dice que use BCrypt
        return authProvider;
    }

    // BEAN 4: EL HASHEADOR DE CONTRASEÑAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}