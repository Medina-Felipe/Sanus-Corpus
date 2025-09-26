package com.backendpill.catalog.infrastructure;


import com.backendpill.catalog.application.BrandService;
import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.TypeMedicine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brand")
public class BrandCotroller {

    private final BrandService brandService;

    public BrandCotroller(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/id")
    public Optional<Brand> findById(Long id){
        return brandService.findById(id);
    }

    @GetMapping("/name")
    public Optional<Brand> findByName(String name){
        return brandService.findByName(name);
    }

    @GetMapping("/products")
    public List<Brand> findByProducts(List<Product> products){
        return brandService.findByProducts(products);
    }

    @GetMapping("/typeMedicines")
    public Optional<Brand> findByTypeMedicines(TypeMedicine typeMedicines){
        return brandService.findByTypeMedicines(typeMedicines);
    }
}
