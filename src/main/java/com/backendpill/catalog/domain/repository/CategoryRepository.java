package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Category;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (Interface) para la gestión de categorías.
 */
public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    /**
     * Recupera múltiples categorías basándose en una lista de IDs.
     * Útil para operaciones de actualización en lote o asignación masiva.
     *
     * @param ids Iterable de identificadores.
     * @return Lista de categorías encontradas.
     */
    List<Category> findAllById(Iterable<Long> ids);

    void deleteById(Long id);

    boolean existsById(Long id);
}