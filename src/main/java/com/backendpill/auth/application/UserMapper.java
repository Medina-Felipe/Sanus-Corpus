package com.backendpill.auth.application;

import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import com.backendpill.auth.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils; // Utilidad de Spring para chequear Strings

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public User toEntity(UserRequest request) {
        if (request == null) return null;

        // Nota: No seteamos password ni rol aquí.
        // El servicio se encarga de eso.
        return User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }

    // --- ¡ESTE ES EL MÉTODO QUE TE FALTABA! ---
    public void updateUserFromDto(UserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        // Solo actualizamos si el campo no es nulo y tiene texto
        if (StringUtils.hasText(request.name())) {
            user.setName(request.name());
        }
        if (StringUtils.hasText(request.lastName())) {
            user.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            user.setPhoneNumber(request.phoneNumber());
        }

        // NOTA DE ARQUITECTURA:
        // No actualizamos email ni password aquí.
        // El email suele ser el identificador (complicado de cambiar)
        // y la password requiere encriptación (responsabilidad del Servicio).
    }
}