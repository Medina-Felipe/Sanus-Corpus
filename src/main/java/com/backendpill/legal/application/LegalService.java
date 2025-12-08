package com.backendpill.legal.application;

import com.backendpill.legal.application.dtos.LegalDocumentRequest;
import com.backendpill.legal.application.dtos.LegalDocumentResponse;
import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import com.backendpill.legal.domain.repository.LegalRepository;
import com.backendpill.shared.domain.BusinessException;
import com.backendpill.shared.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LegalService {

    private final LegalRepository legalRepository;

    @Transactional
    public LegalDocumentResponse saveOrUpdate(LegalDocumentRequest request) {
        // Buscamos si ya existe un doc de este tipo
        LegalDocument document = legalRepository.findByType(request.type())
                .orElse(LegalDocument.builder()
                        .type(request.type())
                        .version(0) // Empezará en 1 al guardar
                        .active(true)
                        .content("") // Inicial vacío para evitar null pointer en updateContent
                        .build());

        // Método de dominio que sube la versión si cambia el texto
        document.updateContent(request.content());

        LegalDocument saved = legalRepository.save(document);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public LegalDocumentResponse findByType(DocumentType type) {
        return legalRepository.findByType(type)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Documento legal no encontrado: " + type));
    }

    @Transactional(readOnly = true)
    public List<LegalDocumentResponse> findAll() {
        return legalRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // Mapper interno simple (al ser un solo método, no creamos clase Mapper aparte)
    private LegalDocumentResponse toResponse(LegalDocument doc) {
        return new LegalDocumentResponse(
                doc.getId(),
                doc.getType(),
                doc.getContent(),
                doc.getVersion(),
                doc.getUpdatedAt() != null ? doc.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE) : null
        );
    }
}