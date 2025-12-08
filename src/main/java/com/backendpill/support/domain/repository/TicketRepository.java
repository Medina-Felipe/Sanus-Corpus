package com.backendpill.support.domain.repository;

import com.backendpill.support.domain.SupportTicket;
import com.backendpill.support.domain.TicketStatus;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    SupportTicket save(SupportTicket ticket);
    Optional<SupportTicket> findById(Long id);
    List<SupportTicket> findByUserId(Long userId);
    List<SupportTicket> findByStatus(TicketStatus status);
    List<SupportTicket> findAll();
}