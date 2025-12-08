package com.backendpill.support.application.dtos;

import com.backendpill.support.domain.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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