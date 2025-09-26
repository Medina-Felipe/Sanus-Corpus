package com.backendpill.support.infrastructure;

import com.backendpill.support.domain.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SupportRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findById(Long id);

    Optional<SupportTicket> findByTitle(String title);

    Optional<SupportTicket> findByDescription(String description);

    Optional<SupportTicket> findByStatus(String status);

    Optional<SupportTicket> findByCreated(LocalDateTime created);
}
