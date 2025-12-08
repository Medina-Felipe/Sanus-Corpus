package com.backendpill.legal.application.dtos;

import com.backendpill.legal.domain.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LegalDocumentRequest(
        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentType type,

        @NotBlank(message = "El contenido no puede estar vacío")
        String content
) {}