package com.backendpill.catalog.infrastructure;


import com.backendpill.catalog.application.CategoryService;
import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/id")
    public Optional<Category> findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/name")
    public Optional<Category> findByName(String name) {
        return categoryService.findByName(name);
    }

    @GetMapping("/description")
    public Optional<Category> findByDescription(String description) {
        return categoryService.findByDescription(description);
    }

    @GetMapping("/products")
    public List<Category> findByProducts(List<Product> products) {
        return categoryService.findByProducts(products);
    }

}
