package com.backendpill.catalog.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Long id;
    private String name;
    private double price;
    private String description;
    @OneToMany//(cascade = CascadeType.)
    private List<Stock> stocks = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Brand> brands = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    private TypeMedicine typeMedicines;

}
