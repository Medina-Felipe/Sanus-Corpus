package com.backendpill.support.domain.repository;

import com.backendpill.support.domain.SupportTicket;
import com.backendpill.support.domain.TicketStatus;

import java.util.List;
import java.util.Optional;

/**
 * Puerto para la persistencia de Tickets de Soporte.
 */
public interface TicketRepository {

    SupportTicket save(SupportTicket ticket);

    Optional<SupportTicket> findById(Long id);

    /**
     * Recupera todos los tickets creados por un usuario específico.
     * Fundamental para la vista "Mis Tickets" en el frontend del cliente.
     *
     * @param userId ID del usuario.
     * @return Lista de tickets del usuario.
     */
    List<SupportTicket> findByUserId(Long userId);

    /**
     * Recupera tickets filtrados por su estado actual.
     * Utilizado por los agentes de soporte para ver colas de trabajo (ej. ver todos los OPEN).
     *
     * @param status Estado a filtrar.
     * @return Lista de tickets en ese estado.
     */
    List<SupportTicket> findByStatus(TicketStatus status);

    List<SupportTicket> findAll();
}