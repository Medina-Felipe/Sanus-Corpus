package com.backendpill.auth.application.dtos;

import com.backendpill.auth.domain.User;

/**
 * DTO que encapsula la respuesta exitosa de una autenticación.
 * Contiene el token de acceso necesario para futuras peticiones y
 * la información básica del usuario autenticado.
 *
 * @param accessToken Token JWT (JSON Web Token) para autorización.
 * @param user Detalles del usuario autenticado (sin datos sensibles).
 */
public record AuthResponse(
        String accessToken,
        UserResponse user
) {}