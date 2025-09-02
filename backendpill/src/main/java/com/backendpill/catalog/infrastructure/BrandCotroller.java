package com.backendpill.catalog.infrastructure;


import com.backendpill.catalog.application.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand/")
public class BrandCotroller {

    private final BrandService brandService;

    public BrandCotroller(BrandService brandService) {
        this.brandService = brandService;
    }
}
