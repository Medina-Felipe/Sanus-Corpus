package com.backendpill.catalog.application;

import com.backendpill.catalog.application.dtos.*;
import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.repository.BrandRepository;
import com.backendpill.shared.domain.BusinessException; // Tu excepción global
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final CatalogMapper mapper;

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

    // Método helper para que ProductService lo use
    @Transactional(readOnly = true)
    public Brand findEntityById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));
    }

    @Transactional
    public void delete(Long id) {
        // Aquí podrías validar si la marca tiene productos antes de borrar
        brandRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BrandResponse findById(Long id) {
        return brandRepository.findById(id)
                .map(mapper::toBrandResponse)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));
    }

    // Falta el update
    @Transactional
    public BrandResponse update(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Marca no encontrada: " + id));

        brand.setName(request.name());
        brand.setDescription(request.description());

        return mapper.toBrandResponse(brandRepository.save(brand));
    }
}