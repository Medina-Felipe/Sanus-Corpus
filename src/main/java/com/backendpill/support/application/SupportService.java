package com.backendpill.support.application;

import com.backendpill.shared.domain.NotFoundException;
import com.backendpill.support.application.dtos.SupportTicketRequest;
import com.backendpill.support.application.dtos.SupportTicketResponse;
import com.backendpill.support.domain.*;
import com.backendpill.support.domain.repository.SupportCategoryRepository;
import com.backendpill.support.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de aplicación que orquesta la lógica de negocio del módulo de Soporte.
 * <p>
 * Coordina la interacción entre las entidades de dominio (Ticket, Categoría)
 * y la capa de persistencia.
 */
@Service
@RequiredArgsConstructor
public class SupportService {

    private final TicketRepository ticketRepository;
    private final SupportCategoryRepository categoryRepository;

    /**
     * Crea un nuevo ticket de soporte asociado a un usuario.
     * <p>
     * <b>Flujo de Negocio:</b>
     * 1. Verifica la existencia de la categoría solicitada (Integridad Referencial).
     * 2. Construye la entidad Ticket con estado inicial {@code OPEN}.
     * 3. Asocia el ticket al usuario autenticado (pasado como argumento seguro).
     * 4. Persiste el ticket.
     *
     * @param userId ID del usuario autenticado (extraído del token).
     * @param request Datos del formulario de soporte.
     * @return El ticket creado en formato DTO.
     * @throws NotFoundException Si la categoría indicada no existe.
     */
    @Transactional
    public SupportTicketResponse createTicket(Long userId, SupportTicketRequest request) {
        // 1. Validar Categoría
        SupportCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Categoría de soporte no encontrada"));

        // 2. Crear Ticket (Builder Pattern)
        SupportTicket ticket = SupportTicket.builder()
                .subject(request.subject())
                .description(request.description())
                .priority(request.priority())
                .status(TicketStatus.OPEN) // Estado inicial por defecto
                .userId(userId)
                .category(category)
                .build();

        // 3. Persistir
        SupportTicket savedTicket = ticketRepository.save(ticket);
        return toResponse(savedTicket);
    }

    /**
     * Recupera el historial de tickets de un usuario específico.
     * Útil para la sección "Mis Reclamos" del perfil de usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de tickets pertenecientes al usuario.
     */
    @Transactional(readOnly = true)
    public List<SupportTicketResponse> getMyTickets(Long userId) {
        return ticketRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Recupera la totalidad de los tickets del sistema.
     * Diseñado para el panel de administración o dashboard de agentes de soporte.
     *
     * @return Lista global de tickets.
     */
    @Transactional(readOnly = true)
    public List<SupportTicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Mapper interno para transformar la Entidad a DTO.
     * Maneja la nulidad de la categoría para evitar {@code NullPointerException}.
     */
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