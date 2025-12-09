package com.backendpill.cart.application.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO que representa el estado completo del carrito de compras.
 * <p>
 * <b>Responsabilidad:</b> Actuar como "Single Source of Truth" (Fuente Única de Verdad)
 * para los cálculos financieros. El backend es quien suma los totales, no el frontend,
 * para evitar manipulaciones maliciosas de precios en el navegador.
 *
 * @param id Identificador del carrito.
 * @param userId Dueño del carrito.
 * @param items Lista de productos enriquecida con detalles del catálogo.
 * @param totalAmount Suma total monetaria de todos los items.
 * @param totalItems Cantidad total de unidades físicas en el carrito.
 */
public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> items,
        BigDecimal totalAmount,
        int totalItems
) {}