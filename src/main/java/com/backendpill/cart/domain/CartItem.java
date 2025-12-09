package com.backendpill.cart.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa una línea de detalle dentro del carrito de compras.
 * <p>
 * Contiene la referencia al producto y la cantidad seleccionada.
 * Esta entidad depende totalmente de {@link Cart} y no debe ser accedida directamente (Weak Entity).
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Referencia al producto del catálogo.
     * <p>
     * <b>Arquitectura:</b> Al igual que con el usuario, almacenamos el ID en lugar
     * de la entidad {@code Product} completa. Esto evita cargar datos pesados del catálogo
     * (imágenes, descripciones) cada vez que manipulamos el carrito, mejorando el rendimiento.
     */
    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    /**
     * Referencia al agregado padre.
     * Necesaria para la navegación bidireccional y persistencia JPA.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}