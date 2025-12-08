package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.CategoryRequest;
import com.backendpill.catalog.application.dtos.CategoryResponse;
import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.repository.CategoryRepository;
import com.backendpill.shared.domain.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CatalogMapper mapper;

    // --- MÉTODOS PÚBLICOS (Retornan DTOs) ---

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        // Validación opcional: verificar si ya existe el nombre
        // if (categoryRepository.existsByName(request.name())) ...

        Category category = mapper.toCategoryEntity(request);
        return mapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(mapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
                .map(mapper::toCategoryResponse)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada con ID: " + id));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada con ID: " + id));

        // Actualizamos los campos
        category.setName(request.name());
        category.setDescription(request.description());

        return mapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Categoría no encontrada con ID: " + id);
        }
        // Nota Arquitectónica: Aquí deberías validar si hay productos usando esta categoría
        // antes de borrarla, para evitar dejar productos "huérfanos" o errores de FK.
        categoryRepository.deleteById(id);
    }

    // --- MÉTODOS INTERNOS (Para uso de ProductService) ---
    // Estos métodos devuelven Entidades, NO DTOs.
    // Solo deben ser usados por otros Servicios del mismo módulo (Catalog).

    @Transactional(readOnly = true)
    public List<Category> findAllEntitiesByIds(Set<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);

        // Validamos que se hayan encontrado todas
        if (categories.size() != ids.size()) {
            throw new BusinessException("Alguna de las categorías indicadas no existe");
        }
        return categories;
    }
}