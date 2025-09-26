package com.backendpill.catalog.infrastructure;

import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.TypeMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findById(Long id);

    Optional<Brand> findByName(String name);

    List<Brand> findByProducts(List<Product> products);

    Optional<Brand> findByTypeMedicines(TypeMedicine typeMedicines);
}
