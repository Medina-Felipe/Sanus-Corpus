package com.backendpill.cart.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la sesión de compra actual de un usuario (Carrito).
 * <p>
 * <b>Patrón Aggregate Root:</b> Esta entidad actúa como la raíz del agregado.
 * Controla el ciclo de vida de sus componentes ({@link CartItem}) y garantiza
 * la consistencia de los datos (ej. evita duplicados sumando cantidades).
 * <p>
 * Cualquier modificación a los items debe hacerse a través de los métodos de esta clase,
 * nunca accediendo a los items directamente desde fuera.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
@EntityListeners(AuditingEntityListener.class)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador del usuario dueño del carrito.
     * <p>
     * <b>Arquitectura (Loose Coupling):</b> Mantenemos solo la referencia por ID
     * para desacoplar este módulo del módulo de {@code Auth}.
     * Se configura como {@code unique = true} para forzar la regla de "un carrito activo por usuario".
     */
    @Column(nullable = false, unique = true)
    private Long userId;

    /**
     * Lista de productos seleccionados.
     * <p>
     * {@code orphanRemoval = true}: Si se elimina un item de esta lista, se borra de la BD.
     * {@code CascadeType.ALL}: Cualquier operación en el Cart se propaga a los items.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // --- MÉTODOS DE DOMINIO (Rich Domain Model) ---

    /**
     * Agrega un producto al carrito gestionando la lógica de fusión.
     * <p>
     * <b>Regla de Negocio:</b> Si el producto ya existe en el carrito, no crea
     * una nueva línea, sino que actualiza la cantidad del item existente.
     * Si no existe, lo añade como nuevo y establece la relación bidireccional.
     *
     * @param newItem El nuevo item a agregar.
     */
    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProductId().equals(newItem.getProductId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        // Si llegamos aquí, es un producto nuevo en el carrito
        items.add(newItem);
        newItem.setCart(this);
    }

    /**
     * Elimina completamente un producto del carrito.
     *
     * @param productId ID del producto a remover.
     */
    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    /**
     * Vacía el carrito por completo.
     * Útil tras confirmar una compra o por solicitud del usuario.
     */
    public void clear() {
        items.clear();
    }
}