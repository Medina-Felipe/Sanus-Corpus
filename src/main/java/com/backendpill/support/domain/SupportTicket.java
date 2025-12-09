package com.backendpill.support.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una solicitud de asistencia técnica o reclamo generado por un usuario.
 * <p>
 * Esta entidad es la raíz del agregado de Soporte.
 * Gestiona la información del problema, su estado actual y la prioridad asignada.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "support_tickets")
@EntityListeners(AuditingEntityListener.class)
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Asunto breve o título del problema. */
    @Column(nullable = false)
    private String subject;

    /** Descripción detallada del incidente reportado por el usuario. */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    /**
     * Identificador del usuario que creó el ticket.
     * <p>
     * <b>Decisión de Arquitectura (Loose Coupling):</b> Se almacena únicamente el ID
     * (referencia débil) en lugar de una relación JPA {@code @ManyToOne} con la entidad {@code User}.
     * <p>
     * Esto desacopla el módulo de Soporte del módulo de Autenticación/Usuarios.
     * Si en el futuro {@code Auth} y {@code Support} se convierten en microservicios separados,
     * esta entidad no requerirá cambios estructurales en la base de datos.
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Categoría temática del ticket (ej. Facturación, Técnico, Envíos).
     * Ayuda a enrutar el ticket al departamento correcto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private SupportCategory category;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // --- MÉTODOS DE DOMINIO (Rich Domain Model) ---

    /**
     * Finaliza el ciclo de vida del ticket.
     * Representa una transición de negocio explícita hacia el estado cerrado.
     */
    public void close() {
        this.status = TicketStatus.CLOSED;
    }

    /**
     * Asigna o reasigna la categoría del ticket para su correcto enrutamiento.
     *
     * @param category La nueva categoría de soporte.
     */
    public void assignCategory(SupportCategory category) {
        this.category = category;
    }

    // --- EQUALS & HASHCODE ---

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SupportTicket that = (SupportTicket) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}