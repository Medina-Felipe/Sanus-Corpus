package com.backendpill.legal.application.dtos;


import com.backendpill.legal.domain.DocumentType;



public record LegalDocumentResponse(
        Long id,
        DocumentType type,
        String content,
        int version,
        String lastUpdated
) {}