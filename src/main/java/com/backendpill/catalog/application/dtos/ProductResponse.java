package com.backendpill.catalog.application.dtos;

import com.backendpill.catalog.domain.TypeMedicine;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO de proyección completa de un Producto.
 * <p>
 * Incluye datos planos y objetos anidados (BrandResponse, CategoryResponse)
 * para facilitar la renderización en el frontend sin necesidad de llamadas adicionales.
 *
 * @param id Identificador único.
 * @param name Nombre del producto.
 * @param slug URL amigable (SEO).
 * @param price Precio actual.
 * @param description Descripción.
 * @param imageUrl URL de la imagen.
 * @param typeMedicine Tipo de medicamento.
 * @param active Estado de visibilidad del producto.
 * @param brand Objeto con detalles de la marca.
 * @param categories Lista de objetos de categorías asociadas.
 * @param stock Cantidad disponible en inventario.
 */
public record ProductResponse(
        Long id,
        String name,
        String slug,
        BigDecimal price,
        String description,
        String imageUrl,
        TypeMedicine typeMedicine,
        boolean active,
        BrandResponse brand,
        Set<CategoryResponse> categories,
        Integer stock
) {}