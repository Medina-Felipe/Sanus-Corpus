package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);

    // Método específico de negocio que añadimos antes
    Optional<Product> findBySlug(String slug);
}