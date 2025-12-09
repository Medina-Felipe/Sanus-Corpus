package com.backendpill.catalog.application.dtos;

import com.backendpill.catalog.domain.TypeMedicine;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO complejo para la creación o edición de un Producto.
 * <p>
 * Este objeto actúa como un "Comando" que agrupa toda la información necesaria
 * para construir un producto y sus relaciones (Marca, Categorías e Inventario inicial).
 *
 * @param name Nombre comercial del producto.
 * @param price Precio unitario. Debe ser mayor a 0.
 * @param description Descripción detallada.
 * @param imageUrl URL de la imagen en el servidor de medios.
 * @param typeMedicine Clasificación del medicamento (Genérico, Marca, Bioequivalente, etc).
 * @param brandId ID de la marca existente a asociar.
 * @param categoryIds Conjunto de IDs de las categorías a las que pertenece el producto.
 * @param stockQuantity (Opcional) Cantidad inicial de inventario al crear el producto.
 */
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

        Set<Long> categoryIds,

        Integer stockQuantity
) {}