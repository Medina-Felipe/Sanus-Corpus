package com.backendpill.catalog.infrastructure;

import com.backendpill.catalog.domain.*;
import com.backendpill.catalog.domain.repository.BrandRepository;
import com.backendpill.catalog.domain.repository.CategoryRepository;
import com.backendpill.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogDataSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (brandRepository.findAll().isEmpty()) {
            log.info(" Iniciando el sembrado de datos (Seeding)...");
            seedCatalog();
            log.info(" Sembrado de datos completado exitosamente.");
        } else {
            log.info(" La base de datos ya tiene datos, se omite el Seeding.");
        }
    }

    private void seedCatalog() {
        // --- CREAR MARCAS ---
        Brand pfizer = Brand.builder().name("Pfizer").description("Gigante farmacéutico global").build();
        Brand bayer = Brand.builder().name("Bayer").description("Innovación en salud y nutrición").build();

        // CORRECCIÓN: Guardamos iterando para evitar conflictos de interfaces
        Arrays.asList(pfizer, bayer).forEach(brandRepository::save);

        // --- CREAR CATEGORÍAS ---
        Category analgesicos = Category.builder().name("Analgésicos").description("Para el dolor").build();
        Category antiinflamatorios = Category.builder().name("Antiinflamatorios").description("Para la inflamación").build();
        Category vitaminas = Category.builder().name("Vitaminas").description("Suplementos").build();

        Arrays.asList(analgesicos, antiinflamatorios, vitaminas).forEach(categoryRepository::save);

        // --- CREAR PRODUCTOS ---
        Product aspirina = Product.builder()
                .name("Aspirina Forte")
                .slug("aspirina-forte-500mg")
                .description("Alivio rápido para el dolor de cabeza.")
                .price(new BigDecimal("5990.00"))
                .typeMedicine(TypeMedicine.BRANDED)
                .active(true)
                .imageUrl("https://cdn.ejemplo.com/aspirina.png")
                .brand(bayer)
                .categories(new HashSet<>(List.of(analgesicos, antiinflamatorios)))
                .build();

        Stock stockAspirina = Stock.builder().quantity(100).product(aspirina).build();
        aspirina.setStock(stockAspirina);

        Product paracetamol = Product.builder()
                .name("Paracetamol 1g")
                .slug("paracetamol-1g-gen")
                .description("Efectivo para la fiebre.")
                .price(new BigDecimal("2500.50"))
                .typeMedicine(TypeMedicine.GENERIC)
                .active(true)
                .imageUrl("https://cdn.ejemplo.com/paracetamol.png")
                .brand(pfizer)
                .categories(new HashSet<>(List.of(analgesicos)))
                .build();

        Stock stockParacetamol = Stock.builder().quantity(500).product(paracetamol).build();
        paracetamol.setStock(stockParacetamol);

        // CORRECCIÓN FINAL
        Arrays.asList(aspirina, paracetamol).forEach(productRepository::save);
    }
}