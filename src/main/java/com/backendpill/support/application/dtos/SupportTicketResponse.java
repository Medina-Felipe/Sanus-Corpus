package com.backendpill.support.application.dtos;

import com.backendpill.support.domain.TicketPriority;
import com.backendpill.support.domain.TicketStatus;
import java.time.LocalDateTime;

public record SupportTicketResponse(
        Long id,
        String subject,
        String description,
        TicketStatus status,
        TicketPriority priority,
        String categoryName,
        Long userId, // Devolvemos el ID del usuario
        LocalDateTime createdAt
) {}