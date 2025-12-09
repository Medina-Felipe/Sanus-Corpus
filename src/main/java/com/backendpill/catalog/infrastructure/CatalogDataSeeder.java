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

/**
 * Componente encargado de la carga inicial de datos (Seeding).
 * <p>
 * Se ejecuta automáticamente al iniciar la aplicación (implementa {@link CommandLineRunner}).
 * Útil para entornos de desarrollo y pruebas para contar con un catálogo base.
 * <p>
 * <b>Nota:</b> En entornos productivos, es recomendable desactivar este componente
 * mediante perfiles (ej. {@code @Profile("!prod")}).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogDataSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * Método principal de ejecución.
     * Verifica si la base de datos está vacía antes de proceder para garantizar idempotencia.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (brandRepository.findAll().isEmpty()) {
            log.info("Sembrando datos iniciales del catálogo...");
            seedCatalog();
            log.info("Sembrado de catálogo completado.");
        } else {
            log.info("El catálogo ya contiene datos. Seeding omitido.");
        }
    }

    /**
     * Lógica de creación de entidades y relaciones.
     * Utiliza persistencia en lote para las relaciones independientes.
     */
    private void seedCatalog() {
        // 1. Crear Marcas
        Brand pfizer = Brand.builder().name("Pfizer").description("Gigante farmacéutico global").build();
        Brand bayer = Brand.builder().name("Bayer").description("Innovación en salud y nutrición").build();

        // Guardamos las referencias para usarlas en los productos
        Arrays.asList(pfizer, bayer).forEach(brandRepository::save);

        // 2. Crear Categorías
        Category analgesicos = Category.builder().name("Analgésicos").description("Para el dolor").build();
        Category antiinflamatorios = Category.builder().name("Antiinflamatorios").description("Para la inflamación").build();
        Category vitaminas = Category.builder().name("Vitaminas").description("Suplementos").build();

        Arrays.asList(analgesicos, antiinflamatorios, vitaminas).forEach(categoryRepository::save);

        // 3. Crear Productos con sus relaciones (Stock, Marca, Categorías)
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

        // Configuración de stock (Relación 1 a 1)
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

        // Persistencia final
        Arrays.asList(aspirina, paracetamol).forEach(productRepository::save);
    }
}