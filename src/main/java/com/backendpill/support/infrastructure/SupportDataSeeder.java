package com.backendpill.support.infrastructure;

import com.backendpill.support.domain.SupportCategory;
import com.backendpill.support.domain.repository.SupportCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
public class SupportDataSeeder implements CommandLineRunner {

    private final SupportCategoryRepository categoryRepository;

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

            // Guardamos las categorías iniciales
            categoryRepository.save(tecnico);
            categoryRepository.save(facturacion);
            categoryRepository.save(general);

            log.info("✅ Categorías de soporte creadas.");
        }
    }
}