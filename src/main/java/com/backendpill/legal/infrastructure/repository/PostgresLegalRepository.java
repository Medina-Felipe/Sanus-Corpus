package com.backendpill.legal.infrastructure.repository;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import com.backendpill.legal.domain.repository.LegalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresLegalRepository extends JpaRepository<LegalDocument, Long>, LegalRepository {
    // Spring Data implementa esto automáticamente
    @Override
    Optional<LegalDocument> findByType(DocumentType type);
}