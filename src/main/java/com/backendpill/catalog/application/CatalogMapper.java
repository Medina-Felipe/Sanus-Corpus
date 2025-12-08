package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.*;
import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CatalogMapper {

    // --- BRAND ---
    public Brand toBrandEntity(BrandRequest request) {
        return Brand.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public BrandResponse toBrandResponse(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription());
    }

    // --- CATEGORY ---
    public Category toCategoryEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    // --- PRODUCT ---
    // Nota: Aquí solo mapeamos datos planos. Las relaciones (Brand/Category)
    // las inyectará el Servicio, porque el Mapper no debe llamar a la Base de Datos.
    public Product toProductEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .typeMedicine(request.typeMedicine())
                .active(true) // Por defecto activo al crear
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getTypeMedicine(),
                product.isActive(),
                toBrandResponse(product.getBrand()), // Reusamos el mapper de Brand
                product.getCategories().stream().map(this::toCategoryResponse).collect(Collectors.toSet()),
                product.getStock() != null ? product.getStock().getQuantity() : 0
        );
    }
}