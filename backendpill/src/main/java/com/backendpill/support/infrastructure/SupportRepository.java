package com.backendpill.support.infrastructure;

import com.backendpill.support.domain.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<SupportTicket, Long> {
}
