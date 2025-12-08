package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    // ¡NUEVO!


    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    Optional<Product> findBySlug(String slug);
    boolean existsById(Long id);
}