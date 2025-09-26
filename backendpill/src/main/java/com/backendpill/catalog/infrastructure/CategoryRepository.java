package com.backendpill.catalog.infrastructure;

import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    Optional<Category> findByDescription(String description);

    List<Category> findByProducts(List<Product> products);
}
