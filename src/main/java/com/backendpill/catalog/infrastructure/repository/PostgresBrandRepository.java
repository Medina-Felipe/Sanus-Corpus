package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.repository.BrandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementación tecnológica del repositorio de Marcas.
 * <p>
 * <b>Patrón Adaptador:</b> Esta interfaz extiende {@link JpaRepository} para obtener
 * la funcionalidad de Spring Data JPA, e implementa {@link BrandRepository}
 * para cumplir el contrato agnóstico definido en la capa de dominio.
 */
@Repository
public interface PostgresBrandRepository extends JpaRepository<Brand, Long>, BrandRepository {
    // La implementación de los métodos estándar (save, findAll, etc.)
    // es generada automáticamente por Spring mediante proxies dinámicos.
}