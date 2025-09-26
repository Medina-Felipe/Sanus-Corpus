package com.backendpill.catalog.infrastructure;

import com.backendpill.catalog.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    void createProduct(Product product);

    Optional<Product> findByName(String name);

    Optional<Product> findByPrice(double price);

    Optional<Product> findByDescription(String description);

    List<Product> findByStocks(List<Integer> stocks);

    List<Product> findByBrands(List<Brand> brands);

    Set<Product> findByCategories(List<Category> categories);

    Optional<Product> findByTypeMedicines(TypeMedicine typeMedicines);

    List<Product> findByFavorite(boolean favorite);
}
