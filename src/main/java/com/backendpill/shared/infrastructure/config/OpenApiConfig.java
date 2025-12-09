package com.backendpill.shared.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación viva de la API bajo el estándar OpenAPI 3.0 (Swagger).
 * <p>
 * Esta clase define los metadatos globales de la API y configura los esquemas de seguridad
 * necesarios para probar los endpoints protegidos directamente desde la interfaz gráfica de Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define la especificación global de OpenAPI.
     * <p>
     * Configuración incluye:
     * <ul>
     * <li>Información general (Título, Versión, Contacto).</li>
     * <li>Esquema de Seguridad (JWT Bearer Token): Permite el botón "Authorize" en la UI.</li>
     * </ul>
     *
     * @return Objeto {@link OpenAPI} con la configuración personalizada.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sanus Corpus API")
                        .version("1.0")
                        .description("Documentación de la API REST para el sistema de farmacia Sanus Corpus.")
                        .contact(new Contact()
                                .name("Equipo de Backend")
                                .email("backend@sanus-corpus.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                // Agrega el requerimiento de seguridad globalmente a la documentación
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        // Define CÓMO funciona la seguridad (Bearer Token JWT)
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}