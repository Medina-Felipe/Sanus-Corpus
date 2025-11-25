package com.backendpill.auth.application.DTOs;

import com.backendpill.auth.domain.Role;

// Añadimos phoneNumber para que sea consistente con el UserMapper y UserRequest
public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber, // <-- CORRECCIÓN AQUÍ
        Role role
) {}