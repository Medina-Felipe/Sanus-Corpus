package com.backendpill.cart.application.dtos;

import java.math.BigDecimal;

/**
 * DTO que representa una línea de detalle en la respuesta del carrito.
 * <p>
 * <b>Patrón Data Enrichment:</b> Este objeto combina datos transaccionales (cantidad)
 * con datos maestros del catálogo (nombre, imagen, precio). Esto permite que la vista
 * (Frontend) pueda renderizar la tarjeta del producto sin hacer peticiones adicionales.
 *
 * @param productId ID del producto.
 * @param productName Nombre comercial (proviene del Catálogo).
 * @param productSlug URL amigable (proviene del Catálogo).
 * @param productImageUrl URL de la imagen (proviene del Catálogo).
 * @param unitPrice Precio unitario vigente al momento de la consulta.
 * @param quantity Cantidad seleccionada por el usuario.
 * @param subtotal Cálculo de {@code unitPrice * quantity}.
 */
public record CartItemResponse(
        Long productId,
        String productName,
        String productSlug,
        String productImageUrl,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {}