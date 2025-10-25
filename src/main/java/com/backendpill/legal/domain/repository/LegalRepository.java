package com.backendpill.legal.domain.repository;

import com.backendpill.legal.domain.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepository extends JpaRepository<LegalDocument, Long> {
}
