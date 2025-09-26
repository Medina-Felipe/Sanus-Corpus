package com.backendpill.legal.application;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import com.backendpill.legal.infrastructure.LegalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LegalService {

    private final LegalRepository legalRepository;

    public LegalService(LegalRepository legalRepository) {
        this.legalRepository = legalRepository;
    }

    @Transactional
    public List<LegalDocument> findByDocumentType(DocumentType documentType){
        return legalRepository.findByDocumentType(documentType);
    }

    @Transactional
    public Optional<LegalDocument> findByContent(String content){
        return legalRepository.findByContent(content);
    }

    @Transactional
    public Optional<LegalDocument> findByCreated(LocalDateTime created){
        return legalRepository.findByCreated(created);
    }
}
