package com.backendpill.legal.infrastructure;

import com.backendpill.legal.application.LegalService;
import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/legal")
public class LegalController {

    private final LegalService legalService;

    public LegalController(LegalService legalService) {
        this.legalService = legalService;
    }

    @GetMapping("/documentType")
    public List<LegalDocument> findByDocumentType(DocumentType documentType){
        return legalService.findByDocumentType(documentType);
    }

    @GetMapping("/content")
    public Optional<LegalDocument> findByContent(String content){
        return legalService.findByContent(content);
    }

    @GetMapping("/created")
    public Optional<LegalDocument> findByCreated(LocalDateTime created){
        return legalService.findByCreated(created);
    }
}
