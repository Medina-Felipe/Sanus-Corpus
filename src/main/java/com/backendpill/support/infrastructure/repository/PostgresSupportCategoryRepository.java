package com.backendpill.support.infrastructure.repository;

import com.backendpill.support.domain.SupportCategory;
import com.backendpill.support.domain.repository.SupportCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementación JPA para el repositorio de Categorías de Soporte.
 * Adapta la interfaz de dominio a la tecnología de persistencia de Spring.
 */
@Repository
public interface PostgresSupportCategoryRepository extends JpaRepository<SupportCategory, Long>, SupportCategoryRepository {
    // La implementación es provista automáticamente por el proxy de Spring Data.
}