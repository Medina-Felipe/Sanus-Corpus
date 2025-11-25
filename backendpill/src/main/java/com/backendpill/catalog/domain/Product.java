package com.backendpill.catalog.domain;

import com.backendpill.auth.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    private double price; // Considera usar BigDecimal para dinero

    @Lob
    private String description;

    // Esto ahora es correcto, porque 'Stock.java' tiene el campo "product"
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default // <-- CORRECCIÓN DE WARNING
    private Set<Category> categories = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMedicine typeMedicine; // Nombre corregido

    // Esto ahora es correcto, porque 'User.java' tiene el campo "favorites"
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
    @Builder.Default // <-- CORRECCIÓN DE WARNING
    private Set<User> favoritedBy = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}