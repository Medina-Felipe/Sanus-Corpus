package com.backendpill.auth.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la solicitud de inicio de sesión.
 * Incluye validaciones básicas de formato y obligatoriedad.
 *
 * @param email Correo electrónico del usuario. Debe tener formato válido.
 * @param password Contraseña en texto plano.
 */
public record LoginRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {}