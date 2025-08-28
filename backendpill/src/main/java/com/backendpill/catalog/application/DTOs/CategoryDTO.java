package com.backendpill.catalog.application.DTOs;


import com.backendpill.catalog.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private String name;
    private String description;
    private List<Product> products;

}
