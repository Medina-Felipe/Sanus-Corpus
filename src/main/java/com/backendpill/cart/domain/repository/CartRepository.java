package com.backendpill.cart.domain.repository;

import com.backendpill.cart.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Puerto para la persistencia del agregado Carrito.
 */

public interface CartRepository {

    /**
     * Guarda el estado actual del carrito y todos sus items (por cascada).
     * @param cart El carrito a guardar.
     * @return El carrito persistido.
     */
    Cart save(Cart cart);

    /**
     * Recupera el carrito activo de un usuario.
     *
     * @param userId ID del usuario.
     * @return Optional con el carrito si existe.
     */
    Optional<Cart> findByUserId(Long userId);

    /**
     * Elimina el carrito.
     * Se debe invocar una vez que la orden de compra ha sido generada exitosamente.
     *
     * @param id ID del carrito.
     */
    void deleteById(Long id);
}