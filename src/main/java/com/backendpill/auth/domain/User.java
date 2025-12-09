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

/**
 * Representa a un usuario registrado en la plataforma.
 * <p>
 * Esta entidad gestiona la información de credenciales, datos personales
 * y relaciones de dominio como la lista de productos favoritos.
 * Utiliza {@link AuditingEntityListener} para el rastreo automático de fechas.
 */
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

    /**
     * Conjunto de productos marcados como favoritos por el usuario.
     * <p>
     * Se utiliza {@code FetchType.LAZY} para rendimiento.
     * <b>Nota:</b> El acceso a esta colección debe realizarse dentro de una transacción activa.
     * El setter directo está bloqueado para forzar el uso de los métodos de dominio {@code addFavorite} y {@code removeFavorite}.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<Product> favorites = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /**
     * Agrega un producto a la lista de favoritos del usuario manteniendo la coherencia del modelo.
     * <p>
     * Este método es parte del <i>Rich Domain Model</i> y evita la manipulación directa de la colección.
     *
     * @param product El producto a agregar. Si es {@code null}, la operación se ignora.
     */
    public void addFavorite(Product product) {
        if (product != null) {
            this.favorites.add(product);
        }
    }

    /**
     * Elimina un producto de la lista de favoritos.
     *
     * @param product El producto a remover.
     */
    public void removeFavorite(Product product) {
        this.favorites.remove(product);
    }

    /**
     * Obtiene el nombre completo del usuario concatenando nombre y apellido.
     *
     * @return Una cadena con el formato "Nombre Apellido".
     */
    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    /**
     * Compara la igualdad de esta entidad con otro objeto.
     * <p>
     * Implementación robusta para JPA que maneja correctamente los {@link HibernateProxy}.
     * Dos entidades se consideran iguales si tienen el mismo ID de base de datos.
     * Si la entidad no ha sido persistida (ID nulo), la igualdad se basa en la referencia de memoria.
     *
     * @param o El objeto con el que comparar.
     * @return {@code true} si son la misma entidad, {@code false} en caso contrario.
     */
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

    /**
     * Genera un código hash consistente para la entidad.
     * <p>
     * Retorna el hash de la clase subyacente para garantizar consistencia incluso
     * cuando el objeto es un proxy de Hibernate y sus campos aún no se han inicializado.
     *
     * @return El código hash de la entidad.
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}