package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.ProductRequest;
import com.backendpill.catalog.application.dtos.ProductResponse;
import com.backendpill.catalog.domain.*;
import com.backendpill.catalog.domain.repository.CategoryRepository;
import com.backendpill.catalog.domain.repository.ProductRepository;
import com.backendpill.shared.domain.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID; // Para generar slugs simples por ahora

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService; // Comunicación entre servicios del mismo módulo
    private final CategoryRepository categoryRepository; // Acceso directo a repo auxiliar
    private final CatalogMapper mapper;

    @Transactional
    public ProductResponse create(ProductRequest request) {
        // 1. Convertir datos básicos
        Product product = mapper.toProductEntity(request);

        // 2. Generar Slug (URL amigable)
        // En un sistema real, usarías una librería para "slugify" el nombre (ej: "Mi Producto" -> "mi-producto")
        // Aquí usaremos algo simple + UUID para evitar colisiones
        String slug = request.name().toLowerCase().replace(" ", "-") + "-" + UUID.randomUUID().toString().substring(0, 8);
        product.setSlug(slug);

        // 3. Buscar y asignar Marca (Relación ManyToOne)
        Brand brand = brandService.findEntityById(request.brandId());
        product.setBrand(brand);

        // 4. Buscar y asignar Categorías (Relación ManyToMany)
        if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(request.categoryIds());
            if (categories.size() != request.categoryIds().size()) {
                throw new BusinessException("Alguna de las categorías indicadas no existe");
            }
            product.setCategories(new HashSet<>(categories));
        }

        // 5. Crear Stock inicial (Relación OneToOne)
        if (request.stockQuantity() != null) {
            Stock stock = Stock.builder()
                    .quantity(request.stockQuantity())
                    .product(product) // Vinculación bidireccional
                    .build();
            product.setStock(stock);
        }

        // 6. Guardar (Gracias al Cascade, se guarda el Stock también)
        Product savedProduct = productRepository.save(product);

        return mapper.toProductResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        // Actualizamos campos básicos
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setTypeMedicine(request.typeMedicine());
        product.setImageUrl(request.imageUrl());

        // Actualizar relaciones (Marca)
        if (!product.getBrand().getId().equals(request.brandId())) {
            Brand newBrand = brandService.findEntityById(request.brandId());
            product.setBrand(newBrand);
        }

        // Actualizar Categorías (requiere lógica de Sets, simplificado aquí)
        // ... (Para un update completo se requiere recalcular las categorías)

        return mapper.toProductResponse(productRepository.save(product));
    }

    // Falta el delete
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }
}