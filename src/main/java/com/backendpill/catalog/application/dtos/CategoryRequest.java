package com.backendpill.catalog.application.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para la gestión de Categorías.
 *
 * @param name Nombre de la categoría. Debe ser único en el sistema (validado en servicio).
 * @param description Descripción detallada.
 */
public record CategoryRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String description
) {}