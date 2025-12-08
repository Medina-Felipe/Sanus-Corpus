package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    Brand save(Brand brand);
    Optional<Brand> findById(Long id);
    List<Brand> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}