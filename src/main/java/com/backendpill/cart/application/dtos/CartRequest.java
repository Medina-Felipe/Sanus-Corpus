package com.backendpill.cart.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada (Command) para agregar o modificar items en el carrito.
 * <p>
 * Se enfoca en la intención del usuario: "Quiero X cantidad del producto Y".
 *
 * @param productId ID del producto a agregar.
 * @param quantity Cantidad deseada. Debe ser al menos 1.
 */
public record CartRequest(
        @NotNull(message = "El ID del producto es obligatorio")
        Long productId,

        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        int quantity
) {}