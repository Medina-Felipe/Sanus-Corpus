package com.backendpill.catalog.application.dtos;

/**
 * DTO de salida para Categorías.
 *
 * @param id Identificador único.
 * @param name Nombre de la categoría.
 * @param description Descripción.
 */
public record CategoryResponse(
        Long id,
        String name,
        String description
) {}