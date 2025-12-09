package com.backendpill.legal.application.dtos;

import com.backendpill.legal.domain.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para la publicación de documentos legales.
 * <p>
 * Este objeto actúa como un comando unificado para crear o actualizar (Upsert).
 * Al enviar este request, se está solicitando que el contenido proporcionado
 * pase a ser la versión vigente para ese tipo de documento.
 *
 * @param type El tipo de documento legal (ej. TÉRMINOS Y CONDICIONES).
 * @param content El texto completo del contrato (formato HTML o Texto Plano).
 */
public record LegalDocumentRequest(
        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentType type,

        @NotBlank(message = "El contenido no puede estar vacío")
        String content
) {}