package com.backendpill.auth.infrastructure.security;

import com.backendpill.auth.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Wrapper (Envoltorio) que adapta la entidad de dominio {@link User} a la interfaz
 * {@link UserDetails} requerida por Spring Security.
 * <p>
 * Esto permite mantener la entidad de dominio limpia, sin dependencias del framework de seguridad,
 * aislando la lógica de autenticación en la capa de infraestructura.
 */
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    /**
     * Convierte el rol del usuario de dominio en una autoridad de Spring Security.
     * Se añade el prefijo "ROLE_" por convención del framework.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Métodos de control de estado de cuenta.
    // Actualmente configurados para retornar true (siempre activo),
    // pero extensibles si el dominio implementa bloqueo o expiración de cuentas.

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}