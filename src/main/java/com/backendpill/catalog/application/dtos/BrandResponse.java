package com.backendpill.catalog.application.dtos;

/**
 * DTO de salida para presentar información de Marcas al cliente.
 *
 * @param id Identificador único.
 * @param name Nombre de la marca.
 * @param description Descripción de la marca.
 */
public record BrandResponse(
        Long id,
        String name,
        String description
) {}