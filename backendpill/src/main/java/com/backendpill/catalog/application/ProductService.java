package com.backendpill.catalog.application;


import com.backendpill.catalog.domain.*;
import com.backendpill.catalog.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createProduct(Product product){
        productRepository.createProduct(product);
    }

    @Transactional
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    @Transactional
    public Optional<Product> findByPrice(double price){
        return productRepository.findByPrice(price);
    }

    @Transactional
    public Optional<Product> findByDescription(String description){
        return productRepository.findByDescription(description);
    }

    @Transactional
    public List<Product> findByStocks(List<Integer> stocks){
        return productRepository.findByStocks(stocks);
    }

    @Transactional
    public List<Product> findByBrands(List<Brand> brands){
        return productRepository.findByBrands(brands);
    }

    @Transactional
    public Set<Product> findByCategories(List<Category> categories){
        return productRepository.findByCategories(categories);
    }

    @Transactional
    public Optional<Product> findByTypeMedicines(TypeMedicine typeMedicines){
        return productRepository.findByTypeMedicines(typeMedicines);
    }

    @Transactional
    public List<Product> findByFavorite(boolean favorite){
        return productRepository.findByFavorite(favorite);
    }
}
