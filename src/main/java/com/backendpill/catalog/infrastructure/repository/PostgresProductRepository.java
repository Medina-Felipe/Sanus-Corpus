package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresProductRepository extends JpaRepository<Product, Long>, ProductRepository {

    // Spring Data JPA generará la query automáticamente basada en el nombre del método
    @Override
    Optional<Product> findBySlug(String slug);
}