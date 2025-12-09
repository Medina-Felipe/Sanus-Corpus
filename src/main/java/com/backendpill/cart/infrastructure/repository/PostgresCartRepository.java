package com.backendpill.cart.infrastructure.repository;

import com.backendpill.cart.domain.Cart;
import com.backendpill.cart.domain.repository.CartRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA del puerto de persistencia {@link CartRepository}.
 * <p>
 * <b>Patrón Driven Adapter:</b> Adapta la interfaz definida en el dominio (agnóstica)
 * a la tecnología específica de Spring Data JPA y PostgreSQL.
 */
@Repository
public interface PostgresCartRepository extends JpaRepository<Cart, Long>, CartRepository {

    /**
     * Busca el carrito activo asociado a un usuario.
     * <p>
     * Spring Data genera automáticamente la consulta SQL basada en el nombre del método:
     * {@code SELECT * FROM carts WHERE user_id = ?}
     *
     * @param userId ID del usuario propietario.
     * @return Un Optional conteniendo el carrito si existe.
     */
    Optional<Cart> findByUserId(Long userId);
}