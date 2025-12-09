package com.backendpill.legal.domain.repository;

import com.backendpill.legal.domain.DocumentType;
import com.backendpill.legal.domain.LegalDocument;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (Interface) para la persistencia de documentos legales.
 * <p>
 * Define el contrato de acceso a datos para el módulo legal.
 * <b>Nota de Arquitectura:</b> Este repositorio no expone métodos de eliminación ({@code delete}),
 * ya que los documentos legales constituyen registros de auditoría que deben preservarse históricamente.
 * En su lugar, se debe utilizar la desactivación lógica (campo {@code active}).
 */
public interface LegalRepository {

    /**
     * Persiste o actualiza un documento legal.
     * @param document Entidad a guardar.
     * @return El documento persistido.
     */
    LegalDocument save(LegalDocument document);

    /**
     * Busca un documento legal por su tipo específico.
     * Dado que el tipo es único, retorna como máximo un resultado.
     *
     * @param type El tipo de documento a buscar.
     * @return Un Optional con el documento si existe.
     */
    Optional<LegalDocument> findByType(DocumentType type);

    /**
     * Recupera todos los documentos legales del sistema.
     * @return Lista completa de documentos.
     */
    List<LegalDocument> findAll();
}