package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.User;
import org.springframework.stereotype.Component;

/**
 * Componente de aplicación para mapear entre
 * la entidad de dominio User y sus DTOs.
 */
@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().name() // Devuelve el nombre del Enum (ej. "CLIENT")
        );
    }
}