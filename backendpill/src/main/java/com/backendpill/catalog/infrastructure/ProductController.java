package com.backendpill.catalog.infrastructure;
import com.backendpill.catalog.application.ProductService;
import com.backendpill.catalog.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public void createProduct(Product product){
        productService.createProduct(product);
    }

    @GetMapping("/id")
    public Optional<Product> findById(Long id){
        return productService.findById(id);
    }

    @GetMapping("/name")
    public Optional<Product> findByName(String name){
        return productService.findByName(name);
    }

    @GetMapping("/price")
    public Optional<Product> findByPrice(double price){
        return productService.findByPrice(price);
    }

    @GetMapping("/description")
    public Optional<Product> findByDescription(String description){
        return productService.findByDescription(description);
    }

    @GetMapping("/stocks")
    public List<Product> findByStocks(List<Integer> stocks){
        return productService.findByStocks(stocks);
    }

    @GetMapping("/brands")
    public List<Product> findByBrands(List<Brand> brands){
        return productService.findByBrands(brands);
    }

    @GetMapping("/categories")
    public Set<Product> findByCategories(List<Category> categories){
        return productService.findByCategories(categories);
    }

    @GetMapping("/typeMedicines")
    public Optional<Product> findByTypeMedicines(TypeMedicine typeMedicines){
        return productService.findByTypeMedicines(typeMedicines);
    }

    @PutMapping("/favorite")
    public List<Product> findByFavorite(boolean favorite){
        return productService.findByFavorite(favorite);
    }
}
