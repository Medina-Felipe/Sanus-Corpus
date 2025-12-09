package com.backendpill.legal.application.dtos;

import com.backendpill.legal.domain.DocumentType;

/**
 * DTO de salida para la visualización de documentos legales.
 * <p>
 * Proyecta la información esencial para que el frontend pueda renderizar
 * el contrato y mostrar su vigencia.
 *
 * @param id Identificador interno.
 * @param type Tipo de documento.
 * @param content Cuerpo del texto.
 * @param version Número de versión actual (útil para control de cambios).
 * @param lastUpdated Fecha de última modificación formateada como String (ISO Date) para facilitar la lectura.
 */
public record LegalDocumentResponse(
        Long id,
        DocumentType type,
        String content,
        int version,
        String lastUpdated
) {}