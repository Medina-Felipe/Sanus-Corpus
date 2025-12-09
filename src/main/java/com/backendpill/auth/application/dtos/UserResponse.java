package com.backendpill.auth.application.dtos;

import com.backendpill.auth.domain.Role;
import java.time.LocalDateTime;

/**
 * DTO de proyección de datos de usuario para respuestas de API.
 * <p>
 * Diseñado bajo el principio de "Seguridad por Diseño": excluye explícitamente
 * la contraseña y otros datos sensibles de la entidad {@link com.backendpill.auth.domain.User}.
 *
 * @param id Identificador único del usuario.
 * @param name Nombre.
 * @param lastName Apellido.
 * @param email Correo electrónico.
 * @param phoneNumber Teléfono de contacto.
 * @param role Rol asignado en el sistema.
 * @param createdAt Fecha de registro en la plataforma.
 */
public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Role role,
        LocalDateTime createdAt
) {}