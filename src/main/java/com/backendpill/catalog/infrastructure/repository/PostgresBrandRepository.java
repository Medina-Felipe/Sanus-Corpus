package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.repository.BrandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresBrandRepository extends JpaRepository<Brand, Long>, BrandRepository {
    // No es necesario escribir código.
    // JpaRepository ya implementa save, findById, findAll, etc.
    // y coinciden con la firma de BrandRepository.
}