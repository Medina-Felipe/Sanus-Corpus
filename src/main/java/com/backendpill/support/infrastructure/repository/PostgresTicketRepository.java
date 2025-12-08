package com.backendpill.support.infrastructure.repository;

import com.backendpill.support.domain.SupportTicket;
import com.backendpill.support.domain.TicketStatus;
import com.backendpill.support.domain.repository.TicketRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostgresTicketRepository extends JpaRepository<SupportTicket, Long>, TicketRepository {
    // Spring Data implementa automáticamente save, findById, findAll.

    // Estos métodos coinciden por nombre con los campos de la entidad,
    // así que Spring genera la query SQL automáticamente.
    List<SupportTicket> findByUserId(Long userId);
    List<SupportTicket> findByStatus(TicketStatus status);
}