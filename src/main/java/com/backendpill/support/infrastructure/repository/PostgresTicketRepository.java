package com.backendpill.support.infrastructure.repository;

import com.backendpill.support.domain.SupportTicket;
import com.backendpill.support.domain.TicketStatus;
import com.backendpill.support.domain.repository.TicketRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación JPA para el repositorio de Tickets.
 */
@Repository
public interface PostgresTicketRepository extends JpaRepository<SupportTicket, Long>, TicketRepository {

    /**
     * Busca tickets filtrando por el ID del usuario creador.
     * <p>
     * Genera la consulta SQL: {@code SELECT * FROM support_tickets WHERE user_id = ?}
     */
    @Override
    List<SupportTicket> findByUserId(Long userId);

    /**
     * Busca tickets filtrando por su estado actual.
     * <p>
     * Genera la consulta SQL: {@code SELECT * FROM support_tickets WHERE status = ?}
     */
    @Override
    List<SupportTicket> findByStatus(TicketStatus status);
}