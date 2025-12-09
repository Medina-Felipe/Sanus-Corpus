package com.backendpill.support.application.dtos;

import com.backendpill.support.domain.TicketPriority;
import com.backendpill.support.domain.TicketStatus;
import java.time.LocalDateTime;

/**
 * DTO de salida (Proyección) con la información de un ticket.
 * <p>
 * Aplana la estructura de la entidad para facilitar su consumo en el frontend
 * (ej. entregando {@code categoryName} en lugar del objeto categoría completo).
 *
 * @param id Identificador único del ticket.
 * @param subject Asunto.
 * @param description Descripción.
 * @param status Estado actual del flujo de resolución.
 * @param priority Prioridad asignada.
 * @param categoryName Nombre de la categoría asociada.
 * @param userId ID del usuario creador (útil para paneles de administración).
 * @param createdAt Fecha de creación.
 */
public record SupportTicketResponse(
        Long id,
        String subject,
        String description,
        TicketStatus status,
        TicketPriority priority,
        String categoryName,
        Long userId,
        LocalDateTime createdAt
) {}