package com.backendpill.catalog.infrastructure;

import com.backendpill.catalog.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    Optional<Brand> findById(Long id);
    Optional<Brand> findByDescription(String description);

}
