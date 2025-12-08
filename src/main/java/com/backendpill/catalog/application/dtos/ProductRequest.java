package com.backendpill.catalog.application.dtos;

import com.backendpill.catalog.domain.TypeMedicine;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record ProductRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        String description,

        String imageUrl,

        @NotNull(message = "El tipo de medicamento es obligatorio")
        TypeMedicine typeMedicine,

        @NotNull(message = "La marca es obligatoria")
        Long brandId,

        // Podemos recibir una lista de IDs de categorías
        Set<Long> categoryIds,

        // Opcional: Stock inicial
        Integer stockQuantity
) {}