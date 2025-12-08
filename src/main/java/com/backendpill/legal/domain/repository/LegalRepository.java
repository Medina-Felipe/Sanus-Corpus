package com.backendpill.legal.domain.repository;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import java.util.List;
import java.util.Optional;

public interface LegalRepository {
    LegalDocument save(LegalDocument document);
    Optional<LegalDocument> findByType(DocumentType type);
    List<LegalDocument> findAll();
    // No necesitamos delete, los documentos legales no se borran, se desactivan o actualizan.
}