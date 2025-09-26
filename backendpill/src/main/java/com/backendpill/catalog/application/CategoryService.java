package com.backendpill.catalog.application;


import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.infrastructure.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    @Transactional
    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }

    @Transactional
    public Optional<Category> findByDescription(String description){
        return categoryRepository.findByDescription(description);
    }

    @Transactional
    public List<Category> findByProducts(List<Product> products){
        return categoryRepository.findByProducts(products);
    }
}
