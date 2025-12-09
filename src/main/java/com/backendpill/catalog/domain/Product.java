package com.backendpill.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidad central del catálogo que representa un producto vendible.
 * <p>
 * Actúa como <b>Aggregate Root</b> (Raíz del Agregado) para las categorías y el stock
 * desde una perspectiva de dominio. Gestiona su propio ciclo de vida y sus relaciones.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    /**
     * Identificador amigable para URLs (SEO Friendly).
     * Ejemplo: "paracetamol-500mg-20-comprimidos".
     * Debe ser único en todo el sistema.
     */
    @Column(unique = true)
    private String slug;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    private String description;

    /** URL de la imagen referencial del producto almacenada en servicio externo (S3/Cloudinary). */
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMedicine typeMedicine;

    /**
     * Indicador de disponibilidad lógica (Soft Delete).
     * Si es falso, el producto no debe mostrarse en la tienda, pero persiste en base de datos
     * para mantener integridad histórica de ventas.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    // --- RELACIONES ---

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @ToString.Exclude
    private Brand brand;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    // --- MÉTODOS DE DOMINIO (Helpers) ---

    /**
     * Asocia una categoría al producto.
     * Utilizar este método en lugar de {@code getCategories().add()} para mantener el encapsulamiento.
     *
     * @param category La categoría a añadir.
     */
    public void addCategory(Category category) {
        this.categories.add(category);
    }

    /**
     * Desasocia una categoría del producto.
     *
     * @param category La categoría a remover.
     */
    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}