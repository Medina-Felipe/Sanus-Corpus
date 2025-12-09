package com.backendpill.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de beans esenciales para la capa de autenticación.
 * <p>
 * Define cómo Spring Security debe buscar usuarios (UserDetailsService)
 * y cómo debe validar contraseñas (PasswordEncoder).
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserDetailsService userDetailsService;

    /**
     * Define el proveedor de autenticación principal.
     * <p>
     * Se utiliza {@link DaoAuthenticationProvider} para obtener detalles del usuario
     * desde la base de datos y validar la contraseña utilizando BCrypt.
     *
     * @return El proveedor de autenticación configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Expone el {@link AuthenticationManager} como un Bean.
     * Es el componente encargado de orquestar el proceso de login.
     *
     * @param config Configuración de autenticación de Spring.
     * @return El gestor de autenticación.
     * @throws Exception Si ocurre un error al obtener el gestor.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el algoritmo de hash para las contraseñas.
     * <p>
     * Se utiliza BCrypt, que es el estándar actual recomendado por OWASP
     * debido a su capacidad de adaptarse a la potencia de cómputo futura (work factor).
     *
     * @return Instancia de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}