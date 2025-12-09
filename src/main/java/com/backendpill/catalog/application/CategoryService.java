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

/**
 * Servicio de aplicación para la gestión de Categorías.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CatalogMapper mapper;

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
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

        category.setName(request.name());
        category.setDescription(request.description());

        return mapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Recupera múltiples entidades de Categoría basándose en una lista de IDs.
     * <p>
     * <b>Validación:</b> Verifica que todos los IDs solicitados existan en la base de datos.
     * Si alguno falta, se lanza una excepción para mantener la integridad de los datos.
     *
     * @param ids Conjunto de IDs solicitados.
     * @return Lista de entidades Category.
     * @throws BusinessException Si la cantidad de categorías encontradas difiere de las solicitadas.
     */
    @Transactional(readOnly = true)
    public List<Category> findAllEntitiesByIds(Set<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);

        if (categories.size() != ids.size()) {
            throw new BusinessException("Alguna de las categorías indicadas no existe");
        }
        return categories;
    }
}