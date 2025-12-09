package com.backendpill.auth.application;

import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import com.backendpill.auth.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Componente encargado de la transformación de objetos entre la capa de Dominio (Entidades)
 * y la capa de Aplicación/Presentación (DTOs).
 * <p>
 * Centraliza la lógica de mapeo para evitar código repetitivo en los servicios.
 */
@Component
public class UserMapper {

    /**
     * Convierte una entidad {@link User} en un DTO {@link UserResponse}.
     *
     * @param user La entidad a convertir.
     * @return El DTO de respuesta, o {@code null} si la entrada es nula.
     */
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

    /**
     * Convierte una solicitud {@link UserRequest} en una entidad {@link User}.
     * <p>
     * <b>Nota:</b> Campos sensibles o críticos como la contraseña y el rol no se asignan aquí,
     * ya que requieren procesamiento lógico (encriptación) o reglas de negocio (asignación de rol por defecto)
     * que son responsabilidad del servicio.
     *
     * @param request El DTO de solicitud.
     * @return La entidad Usuario parcialmente inicializada.
     */
    public User toEntity(UserRequest request) {
        if (request == null) return null;

        return User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }

    /**
     * Actualiza una entidad {@link User} existente con los datos provenientes de un {@link UserRequest}.
     * <p>
     * Implementa una estrategia de "actualización parcial": solo se modifican los campos
     * que contienen texto válido en el request. El email y la contraseña se ignoran intencionalmente
     * para preservar la identidad y seguridad.
     *
     * @param request DTO con los nuevos datos.
     * @param user Entidad a actualizar (se modifica por referencia).
     */
    public void updateUserFromDto(UserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        if (StringUtils.hasText(request.name())) {
            user.setName(request.name());
        }
        if (StringUtils.hasText(request.lastName())) {
            user.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            user.setPhoneNumber(request.phoneNumber());
        }
    }
}