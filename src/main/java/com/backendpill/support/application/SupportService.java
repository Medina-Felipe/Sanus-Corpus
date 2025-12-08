package com.backendpill.support.application;

import com.backendpill.shared.domain.NotFoundException;
import com.backendpill.support.application.dtos.SupportTicketRequest;
import com.backendpill.support.application.dtos.SupportTicketResponse;
import com.backendpill.support.domain.*;
import com.backendpill.support.domain.repository.SupportCategoryRepository; // Nuevo import
import com.backendpill.support.domain.repository.TicketRepository;          // Nuevo import
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {

    private final TicketRepository ticketRepository;            // Inyección específica
    private final SupportCategoryRepository categoryRepository; // Inyección específica

    @Transactional
    public SupportTicketResponse createTicket(Long userId, SupportTicketRequest request) {
        // 1. Validar Categoría usando el repositorio de categorías
        SupportCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Categoría de soporte no encontrada"));

        // 2. Crear Ticket
        SupportTicket ticket = SupportTicket.builder()
                .subject(request.subject())
                .description(request.description())
                .priority(request.priority())
                .status(TicketStatus.OPEN)
                .userId(userId)
                .category(category)
                .build();

        // 3. Guardar usando el repositorio de tickets
        SupportTicket savedTicket = ticketRepository.save(ticket);
        return toResponse(savedTicket);
    }

    @Transactional(readOnly = true)
    public List<SupportTicketResponse> getMyTickets(Long userId) {
        return ticketRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SupportTicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private SupportTicketResponse toResponse(SupportTicket ticket) {
        return new SupportTicketResponse(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCategory() != null ? ticket.getCategory().getName() : "General",
                ticket.getUserId(),
                ticket.getCreatedAt()
        );
    }
}