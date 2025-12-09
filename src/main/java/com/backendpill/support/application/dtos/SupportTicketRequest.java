package com.backendpill.support.application.dtos;

import com.backendpill.support.domain.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada (Command) para la creación de un nuevo ticket de soporte.
 * <p>
 * <b>Seguridad:</b> No incluimos el campo {@code userId} en este objeto.
 * El ID del usuario se obtiene del Token JWT en el controlador para evitar
 * suplantación de identidad (ID Spoofing).
 *
 * @param subject Título breve del problema.
 * @param description Detalles extensos del incidente.
 * @param priority Nivel de urgencia percibida por el usuario.
 * @param categoryId ID de la categoría a la que pertenece el problema.
 */
public record SupportTicketRequest(
        @NotBlank(message = "El asunto es obligatorio")
        String subject,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "La prioridad es obligatoria")
        TicketPriority priority,

        @NotNull(message = "La categoría es obligatoria")
        Long categoryId
) {}