package com.backendpill.support.application.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada (Command) para la creación o edición de Categorías de Soporte.
 * <p>
 * <b>Responsabilidad:</b> Actúa como barrera de entrada para asegurar la integridad de los datos
 * antes de que lleguen a la lógica de negocio. Utiliza validaciones declarativas (JSR-380)
 * para rechazar peticiones incompletas inmediatamente (HTTP 400).
 *
 * @param name Nombre de la categoría. Es obligatorio para garantizar la unicidad e identificación.
 * @param description Descripción opcional para uso interno o ayuda al usuario.
 */
public record SupportRequest(
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        String name,

        String description
) {}