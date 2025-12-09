package com.backendpill.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Representa el inventario físico disponible para un producto.
 * <p>
 * <b>Diseño de Arquitectura:</b> Se ha separado el Stock del Producto (relación 1 a 1)
 * para permitir estrategias de bloqueo (Locking) independientes. Esto evita que
 * la actualización de un precio bloquee la tabla de inventario durante transacciones concurrentes.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cantidad física disponible en almacén.
     * Debe ser mayor o igual a cero.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Producto asociado a este registro de stock.
     * <p>
     * Relación unidireccional desde Stock hacia Producto para facilitar la gestión
     * de inventario sin necesidad de cargar toda la información del producto si no es necesario.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", unique = true)
    @ToString.Exclude
    private Product product;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Stock stock = (Stock) o;
        return getId() != null && Objects.equals(getId(), stock.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}