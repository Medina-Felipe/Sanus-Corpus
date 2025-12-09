package com.backendpill.legal.application;

import com.backendpill.legal.application.dtos.LegalDocumentRequest;
import com.backendpill.legal.application.dtos.LegalDocumentResponse;
import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import com.backendpill.legal.domain.repository.LegalRepository;
import com.backendpill.shared.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servicio de aplicación para la gestión del ciclo de vida de los documentos legales.
 * <p>
 * Centraliza la lógica de negocio referente a la publicación y recuperación de
 * términos, condiciones y políticas de privacidad.
 */
@Service
@RequiredArgsConstructor
public class LegalService {

    private final LegalRepository legalRepository;

    /**
     * Publica una nueva versión de un documento legal (Estrategia Upsert).
     * <p>
     * <b>Lógica de Negocio:</b>
     * 1. Busca si ya existe un documento del tipo solicitado.
     * 2. Si no existe, instancia uno nuevo con versión 0 y contenido vacío.
     * 3. Invoca al método de dominio {@code updateContent()}, delegando a la entidad
     * la responsabilidad de comparar el texto y aumentar la versión si es necesario (Rich Domain Model).
     * 4. Persiste los cambios.
     *
     * @param request Datos del documento a guardar.
     * @return El documento persistido con su nueva versión.
     */
    @Transactional
    public LegalDocumentResponse saveOrUpdate(LegalDocumentRequest request) {
        LegalDocument document = legalRepository.findByType(request.type())
                .orElse(LegalDocument.builder()
                        .type(request.type())
                        .version(0)
                        .active(true)
                        .content("")
                        .build());

        document.updateContent(request.content());

        LegalDocument saved = legalRepository.save(document);
        return toResponse(saved);
    }

    /**
     * Recupera el documento legal vigente para un tipo específico.
     *
     * @param type El tipo de documento requerido.
     * @return El documento en formato DTO.
     * @throws NotFoundException Si el documento no ha sido creado aún en el sistema.
     */
    @Transactional(readOnly = true)
    public LegalDocumentResponse findByType(DocumentType type) {
        return legalRepository.findByType(type)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Documento legal no encontrado: " + type));
    }

    /**
     * Obtiene el listado completo de documentos legales disponibles.
     *
     * @return Lista de documentos.
     */
    @Transactional(readOnly = true)
    public List<LegalDocumentResponse> findAll() {
        return legalRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Mapper interno (Helper Method) para transformar Entidad a DTO.
     * <p>
     * <b>Decisión de Arquitectura:</b> Dado que la lógica de mapeo es simple y exclusiva
     * de este servicio, se implementa como un método privado en lugar de crear una clase
     * Mapper externa (evitando Over-engineering y manteniendo alta cohesión).
     *
     * @param doc La entidad a convertir.
     * @return El DTO de respuesta.
     */
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