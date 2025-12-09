package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.*;
import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Componente de transformación de objetos (Object Mapping).
 * <p>
 * Responsable de convertir DTOs a Entidades y viceversa.
 * <b>Principio de Diseño:</b> Este mapper es "tonto" (dumb), no contiene lógica de negocio
 * ni realiza consultas a base de datos. Solo transforma datos que ya tiene en memoria.
 */
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

    /**
     * Crea una instancia base de Producto desde el Request.
     * <p>
     * <b>Nota:</b> Las relaciones complejas (Brand, Categories, Stock) no se asignan aquí,
     * ya que requieren búsqueda en base de datos. Esa responsabilidad recae en el Servicio.
     */
    public Product toProductEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .typeMedicine(request.typeMedicine())
                .active(true)
                .build();
    }

    /**
     * Convierte un Producto completo (con sus relaciones cargadas) a un Response.
     * Gestiona la extracción segura del stock (evitando NullPointerException).
     */
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
                toBrandResponse(product.getBrand()),
                product.getCategories().stream().map(this::toCategoryResponse).collect(Collectors.toSet()),
                product.getStock() != null ? product.getStock().getQuantity() : 0
        );
    }
}