package com.backendpill.legal.infrastructure;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LegalRepository extends JpaRepository<LegalDocument, Long> {

    Optional<LegalDocument> findById(Long id);

    List<LegalDocument> findByDocumentType(DocumentType documentType);

    Optional<LegalDocument> findByContent(String content);

    Optional<LegalDocument> findByCreated(LocalDateTime created);

}
