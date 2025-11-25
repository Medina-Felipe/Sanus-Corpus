package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.User;
import org.springframework.stereotype.Component;

/**
 * Componente de aplicación para mapear entre
 * la entidad de dominio User y sus DTOs.
 * * Cumple la función de "Traductor" entre el mundo externo (JSON/DTO)
 * y el mundo interno (Entidades/Base de Datos).
 */
@Component
public class UserMapper {

    // --- 1. DE ENTIDAD A RESPUESTA (Salida / Lectura) ---
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
                user.getRole() // Correcto: Pasamos el Enum Role
        );
    }

    // --- 2. DE REQUEST A ENTIDAD (Entrada / Creación) ---
    // Útil si quieres limpiar el método 'register' del Service en el futuro
    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        // Nota: No seteamos Password ni Role aquí porque
        // eso requiere lógica de negocio (Encodeo, asignación por defecto)
        // que pertenece al Service.
        return User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }

    // --- 3. ACTUALIZAR ENTIDAD EXISTENTE (Entrada / Modificación) ---
    // Útil para el método 'update' del Service.
    // Pasa los datos del DTO a la entidad existente, ignorando nulos.
    public void updateUserFromDto(UserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        if (request.name() != null) user.setName(request.name());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());

        // Nota: El Email lo manejamos en el Service para validar duplicados antes de cambiarlo.
    }
}