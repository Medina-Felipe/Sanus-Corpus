package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.*;
import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.repository.BrandRepository;
import com.backendpill.shared.domain.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio de aplicación para la gestión de Marcas.
 * Maneja la lógica CRUD y la consistencia transaccional.
 */
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final CatalogMapper mapper;

    /**
     * Crea una nueva marca en el catálogo.
     * @param request Datos de la marca.
     * @return La marca creada en formato DTO.
     */
    @Transactional
    public BrandResponse create(BrandRequest request) {
        Brand brand = mapper.toBrandEntity(request);
        return mapper.toBrandResponse(brandRepository.save(brand));
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(mapper::toBrandResponse)
                .toList();
    }

    /**
     * Recupera una Entidad de dominio por su ID.
     * <p>
     * <b>Uso Interno:</b> Este método retorna la Entidad {@link Brand} (no el DTO)
     * y está diseñado para ser consumido por otros servicios del dominio (como ProductService)
     * que necesitan establecer relaciones de clave foránea.
     *
     * @param id ID de la marca.
     * @return La entidad Brand gestionada por JPA.
     * @throws BusinessException Si no existe.
     */
    @Transactional(readOnly = true)
    public Brand findEntityById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));
    }

    /**
     * Elimina una marca.
     * <p>
     * <b>Atención:</b> Esta operación podría fallar a nivel de base de datos si existen
     * productos asociados (Violación de restricción de clave foránea).
     *
     * @param id ID de la marca a eliminar.
     */
    @Transactional
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BrandResponse findById(Long id) {
        return brandRepository.findById(id)
                .map(mapper::toBrandResponse)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));
    }

    @Transactional
    public BrandResponse update(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));

        brand.setName(request.name());
        brand.setDescription(request.description());

        return mapper.toBrandResponse(brandRepository.save(brand));
    }
}