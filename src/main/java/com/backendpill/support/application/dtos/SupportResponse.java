package com.backendpill.support.application.dtos;

/**
 * DTO de salida que representa los datos públicos de una Categoría de Soporte.
 * <p>
 * Este objeto se utiliza para proyectar la entidad {@code SupportCategory} hacia el cliente via API.
 * Es ideal para poblar elementos de interfaz de usuario como listas desplegables (Select/Dropdown)
 * donde el usuario debe elegir el tipo de problema.
 *
 * @param id Identificador único de la categoría.
 * @param name Nombre corto y descriptivo (ej. "Facturación", "Técnico").
 * @param description Detalles sobre qué tipo de tickets abarca esta categoría.
 */
public record SupportResponse(
        Long id,
        String name,
        String description
) {}