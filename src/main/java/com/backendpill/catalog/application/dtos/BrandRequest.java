package com.backendpill.catalog.application.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para la creación y actualización de Marcas.
 * <p>
 * Contiene las validaciones básicas de formato requeridas antes de procesar
 * la lógica de negocio.
 *
 * @param name Nombre de la marca. Obligatorio.
 * @param description Descripción opcional (HTML o texto plano).
 */
public record BrandRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String description
) {}