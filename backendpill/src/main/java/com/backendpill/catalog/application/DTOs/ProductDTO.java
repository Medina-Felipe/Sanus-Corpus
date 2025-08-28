package com.backendpill.catalog.application.DTOs;


import com.backendpill.catalog.domain.Brand;
import com.backendpill.catalog.domain.Stock;
import com.backendpill.catalog.domain.TypeMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private double price;
    private String description;
    private List<Stock> stocks;
    private List<Brand> brands;
    private TypeMedicine typeMedicines;
}
