package com.backendpill.legal.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un documento legal (Términos, Políticas, etc.) vigente en la plataforma.
 * <p>
 * Esta entidad actúa como la "Fuente de Verdad" para los contratos digitales.
 * Incorpora un mecanismo de control de versiones interno para garantizar que se pueda
 * rastrear qué versión exacta aceptó un usuario en un momento dado.
 */
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

    /**
     * Tipo de documento legal.
     * <p>
     * Configurad como único ({@code unique = true}) en base de datos.
     * Regla de Negocio: Solo puede existir una instancia activa de cada tipo de documento
     * para evitar ambigüedades sobre cuál es el contrato vigente.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DocumentType type;

    /**
     * Contenido completo del documento en formato texto (o HTML/Markdown).
     * Se define como {@code TEXT} para soportar grandes volúmenes de caracteres.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * Versión secuencial del documento.
     * Fundamental para la auditoría: permite vincular la aceptación de un usuario
     * con una versión específica del texto.
     */
    @Column(nullable = false)
    private int version;

    /**
     * Estado de visibilidad del documento.
     * Permite "apagar" un documento legal sin eliminarlo físicamente de la base de datos.
     */
    @Column(nullable = false)
    private boolean active;

    /**
     * Fecha de la última modificación.
     * Gestionada automáticamente por el framework de auditoría.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // --- MÉTODOS DE DOMINIO (Rich Domain Model) ---

    /**
     * Actualiza el contenido del documento aplicando reglas de negocio de versionado.
     * <p>
     * Si el contenido nuevo es diferente al actual, se reemplaza el texto y
     * se incrementa automáticamente el número de versión. Esto garantiza la integridad
     * histórica de los cambios.
     *
     * @param newContent El nuevo texto del documento legal.
     */
    public void updateContent(String newContent) {
        if (!newContent.equals(this.content)) {
            this.content = newContent;
            this.version++;
        }
    }

    // --- EQUALS & HASHCODE ---

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