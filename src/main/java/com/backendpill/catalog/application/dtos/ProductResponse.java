package com.backendpill.catalog.application.dtos;

import com.backendpill.catalog.domain.TypeMedicine;

import java.math.BigDecimal;
import java.util.Set;

public record ProductResponse(
        Long id,
        String name,
        String slug,
        BigDecimal price,
        String description,
        String imageUrl,
        TypeMedicine typeMedicine,
        boolean active,
        BrandResponse brand,       // Devolvemos objeto completo
        Set<CategoryResponse> categories, // Devolvemos lista de objetos
        Integer stock // Si decidimos mostrarlo aquí
) {}