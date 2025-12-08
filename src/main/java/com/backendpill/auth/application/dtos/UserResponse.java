package com.backendpill.auth.application.dtos;

import com.backendpill.auth.domain.Role;
import java.time.LocalDateTime; // <-- Importante para mostrar antigüedad

public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Role role,
        LocalDateTime createdAt // <-- Nuevo campo útil para el perfil
) {}