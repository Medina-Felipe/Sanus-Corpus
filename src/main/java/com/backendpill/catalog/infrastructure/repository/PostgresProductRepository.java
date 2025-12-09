package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación JPA para la persistencia de Productos.
 */
@Repository
public interface PostgresProductRepository extends JpaRepository<Product, Long>, ProductRepository {

    /**
     * Consulta derivada (Derived Query Method) para buscar por Slug.
     * <p>
     * Spring Data traduce esto automáticamente a JPQL:
     * {@code SELECT p FROM Product p WHERE p.slug = :slug}
     *
     * @param slug El slug a buscar.
     * @return Optional con el producto.
     */
    @Override
    Optional<Product> findBySlug(String slug);
}