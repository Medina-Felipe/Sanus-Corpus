package com.backendpill.support.infrastructure;

import com.backendpill.support.domain.SupportCategory;
import com.backendpill.support.domain.repository.SupportCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cargador de datos iniciales para el módulo de Soporte.
 * <p>
 * Responsable de poblar la base de datos con las categorías de soporte predeterminadas
 * al iniciar la aplicación, garantizando que el sistema sea operativo desde el primer despliegue.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SupportDataSeeder implements CommandLineRunner {

    private final SupportCategoryRepository categoryRepository;

    /**
     * Ejecuta la lógica de sembrado de datos.
     * <p>
     * Implementa un chequeo de <b>Idempotencia</b>: verifica si la tabla ya tiene datos
     * antes de insertar, evitando duplicados en reinicios sucesivos del servidor.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoryRepository.findAll().isEmpty()) {
            log.info("🎫 Iniciando el sembrado de datos de Soporte...");

            SupportCategory tecnico = SupportCategory.builder()
                    .name("Soporte Técnico")
                    .description("Problemas con la plataforma o errores.")
                    .build();

            SupportCategory facturacion = SupportCategory.builder()
                    .name("Facturación")
                    .description("Dudas sobre pagos o reembolsos.")
                    .build();

            SupportCategory general = SupportCategory.builder()
                    .name("Consultas Generales")
                    .description("Información sobre productos o servicios.")
                    .build();

            categoryRepository.save(tecnico);
            categoryRepository.save(facturacion);
            categoryRepository.save(general);

            log.info("✅ Categorías de soporte creadas exitosamente.");
        }
    }
}