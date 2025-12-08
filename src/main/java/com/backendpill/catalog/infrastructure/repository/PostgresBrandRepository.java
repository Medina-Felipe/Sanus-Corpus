package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.repository.BrandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostgresBrandRepository extends JpaRepository<Brand, Long>, BrandRepository {
    // No es necesario implementar saveAll manualmente.
    // JpaRepository ya lo tiene y cumple con el contrato de BrandRepository.



}