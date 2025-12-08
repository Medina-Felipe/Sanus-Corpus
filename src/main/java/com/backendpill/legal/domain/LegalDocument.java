package com.backendpill.legal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "legal_documents")
@EntityListeners(AuditingEntityListener.class)
public class LegalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true) // Solo un documento por tipo
    private DocumentType type;

    @Column(columnDefinition = "TEXT", nullable = false) // "TEXT" permite strings largos en Postgres
    private String content;

    @Column(nullable = false)
    private int version; // Útil para saber si el usuario aceptó la versión 1 o la 2

    @Column(nullable = false)
    private boolean active;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // --- MÉTODOS DE DOMINIO ---

    public void updateContent(String newContent) {
        if (!newContent.equals(this.content)) {
            this.content = newContent;
            this.version++; // Subimos versión automáticamente al cambiar texto
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LegalDocument that = (LegalDocument) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}