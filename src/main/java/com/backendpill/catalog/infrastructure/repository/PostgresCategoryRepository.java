package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementación JPA para la persistencia de Categorías.
 */
@Repository
public interface PostgresCategoryRepository extends JpaRepository<Category, Long>, CategoryRepository {
    // Spring Data JPA provee automáticamente la implementación optimizada
    // para findAllById(Iterable<Long> ids) usando cláusulas IN de SQL.
}