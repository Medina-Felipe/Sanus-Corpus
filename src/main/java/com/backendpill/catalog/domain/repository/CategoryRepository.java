package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    List<Category> findAllById(Iterable<Long> ids); // Necesario para ProductService
    void deleteById(Long id);
    boolean existsById(Long id);
}