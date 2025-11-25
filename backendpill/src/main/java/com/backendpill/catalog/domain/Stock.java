package com.backendpill.catalog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    // --- ¡AQUÍ ESTÁ LA CORRECCIÓN DE 'STOCK'! ---
    // Este es el lado "dueño" de la relación OneToOne.
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", unique = true)
    private Product product; // Este es el campo que Hibernate buscaba
    // --- FIN DE LA CORRECCIÓN ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id != null && id.equals(stock.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}