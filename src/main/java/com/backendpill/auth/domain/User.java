package com.backendpill.auth.domain;

import com.backendpill.catalog.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // --- RELACIONES ---

    // ARQUITECTURA: Nota importante.
    // Al usar FetchType.LAZY, asegúrate de usar @Transactional en el Servicio
    // cuando accedas a los favoritos, o tendrás una LazyInitializationException.
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    @Setter(AccessLevel.NONE) // Bloqueamos el set directo para proteger la lógica
    private Set<Product> favorites = new HashSet<>();

    // --- AUDITORÍA ---
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    // --- MÉTODOS DE DOMINIO (Rich Domain Model) ---
    // Un arquitecto prefiere esto a usar user.getFavorites().add(product) en el servicio.

    public void addFavorite(Product product) {
        if (product != null) {
            this.favorites.add(product);
            // Si la relación fuera bidireccional, aquí haríamos: product.getUsers().add(this);
        }
    }

    public void removeFavorite(Product product) {
        this.favorites.remove(product);
    }

    // Método auxiliar para obtener nombre completo
    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    // --- EQUALS & HASHCODE (Crucial para JPA y Sets) ---
    // Lombok estándar falla con JPA entities. Esta es la forma segura:
    // Compara solo por ID si existe, o por referencia si es nuevo.

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}