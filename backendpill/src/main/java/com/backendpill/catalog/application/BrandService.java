package com.backendpill.catalog.application;

import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.TypeMedicine;
import com.backendpill.catalog.infrastructure.BrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public Optional<Brand> findById(Long id){
        return brandRepository.findById(id);
    }

    @Transactional
    public Optional<Brand> findByName(String name){
        return brandRepository.findByName(name);
    }

    @Transactional
    public List<Brand> findByProducts(List<Product> products){
        return brandRepository.findByProducts(products);
    }

    @Transactional
    public Optional<Brand> findByTypeMedicines(TypeMedicine typeMedicines){
        return brandRepository.findByTypeMedicines(typeMedicines);
    }
}
