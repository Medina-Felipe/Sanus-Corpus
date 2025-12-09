package com.backendpill.legal.infrastructure.repository;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import com.backendpill.legal.domain.repository.LegalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA del repositorio de documentos legales.
 * <p>
 * <b>Patrón Driven Adapter:</b> Esta interfaz adapta el contrato de dominio {@link LegalRepository}
 * a la tecnología de persistencia Spring Data JPA. Permite que el dominio permanezca agnóstico
 * a la base de datos (PostgreSQL) mientras aprovechamos las facilidades del framework.
 */
@Repository
public interface PostgresLegalRepository extends JpaRepository<LegalDocument, Long>, LegalRepository {

    /**
     * Consulta derivada (Derived Query Method) para buscar documentos por su tipo.
     * <p>
     * Spring Data genera automáticamente la consulta SQL equivalente a:
     * {@code SELECT * FROM legal_documents WHERE type = ?}
     *
     * @param type El tipo de documento a buscar.
     * @return Un Optional que contiene el documento si existe.
     */
    @Override
    Optional<LegalDocument> findByType(DocumentType type);
}