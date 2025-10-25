package com.backendpill.auth.application.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; // <-- 1. IMPORTAR

public record UserRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @Email @NotBlank String email,

        // 2. AÑADIMOS @Size para fortaleza de contraseña
        @NotBlank
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        // 3. Dejamos phoneNumber como opcional (sin @NotBlank)
        // Si es obligatorio, solo añade @NotBlank aquí.
        String phoneNumber
) {}