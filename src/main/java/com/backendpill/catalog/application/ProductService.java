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
import java.util.UUID;

/**
 * Servicio principal del catálogo que gestiona el ciclo de vida de los Productos.
 * <p>
 * Actúa como orquestador (Facade) coordinando:
 * 1. Persistencia del Producto.
 * 2. Asociación con Marcas (vía BrandService).
 * 3. Asociación con Categorías (vía CategoryRepository).
 * 4. Creación inicial del Stock.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryRepository categoryRepository;
    private final CatalogMapper mapper;

    /**
     * Crea un nuevo producto con todas sus relaciones e inventario inicial.
     * <p>
     * <b>Flujo Transaccional:</b>
     * 1. Transforma DTO a Entidad base.
     * 2. Genera Slug único.
     * 3. Recupera y asigna la entidad {@link Brand}.
     * 4. Recupera y asigna las entidades {@link Category}.
     * 5. Instancia y vincula la entidad {@link Stock} (si se provee cantidad).
     * 6. Persiste todo en una única operación atómica (Cascade).
     *
     * @param request Datos de entrada del producto.
     * @return El producto creado con su ID generado.
     */
    @Transactional
    public ProductResponse create(ProductRequest request) {
        // 1. Convertir datos básicos
        Product product = mapper.toProductEntity(request);

        // 2. Generar Slug (Estrategia simple con UUID para evitar colisiones en MVP)
        String slug = request.name().toLowerCase().replace(" ", "-") + "-" + UUID.randomUUID().toString().substring(0, 8);
        product.setSlug(slug);

        // 3. Buscar y asignar Marca (Comunicación Inter-Servicio)
        Brand brand = brandService.findEntityById(request.brandId());
        product.setBrand(brand);

        // 4. Buscar y asignar Categorías
        if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(request.categoryIds());
            if (categories.size() != request.categoryIds().size()) {
                throw new BusinessException("Alguna de las categorías indicadas no existe");
            }
            product.setCategories(new HashSet<>(categories));
        }

        // 5. Crear Stock inicial (Relación 1 a 1 bidireccional en memoria)
        if (request.stockQuantity() != null) {
            Stock stock = Stock.builder()
                    .quantity(request.stockQuantity())
                    .product(product)
                    .build();
            product.setStock(stock);
        }

        // 6. Guardar (El CascadeType.ALL en Product propaga el save al Stock)
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

    /**
     * Actualiza la información de un producto existente.
     * <p>
     * <b>Nota de Arquitectura:</b> Las actualizaciones de relaciones (cambiar de marca)
     * se manejan verificando el estado actual vs el nuevo request para optimizar consultas.
     *
     * @param id ID del producto.
     * @param request Nuevos datos.
     * @return Producto actualizado.
     */
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        // Actualizamos campos básicos (Dirty checking de Hibernate detectará cambios)
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setTypeMedicine(request.typeMedicine());
        product.setImageUrl(request.imageUrl());

        // Actualizar Marca si ha cambiado
        if (!product.getBrand().getId().equals(request.brandId())) {
            Brand newBrand = brandService.findEntityById(request.brandId());
            product.setBrand(newBrand);
        }

        // TODO: La actualización de categorías requiere lógica de comparación de Sets (add/remove)
        // para un manejo eficiente. Simplificado para este MVP.

        return mapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }
}