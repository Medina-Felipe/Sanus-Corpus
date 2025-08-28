package com.backendpill.catalog.application;


import com.backendpill.catalog.infrastructure.ProductRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
