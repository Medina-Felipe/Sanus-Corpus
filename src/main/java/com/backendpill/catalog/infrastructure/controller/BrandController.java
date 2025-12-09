package com.backendpill.catalog.infrastructure.controller;

import com.backendpill.catalog.application.BrandService;
import com.backendpill.catalog.application.dtos.BrandRequest;
import com.backendpill.catalog.application.dtos.BrandResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la exposición de recursos de Marcas.
 * <p>
 * Actúa como un "Driving Adapter" (Adaptador Conductor), traduciendo las peticiones HTTP
 * en llamadas al servicio de aplicación {@link BrandService}.
 */
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    /**
     * Crea una nueva marca en el sistema.
     *
     * @param request DTO con la información de creación, validado por {@code @Valid}.
     * @return 201 Created con el recurso creado en el cuerpo de la respuesta.
     */
    @PostMapping
    public ResponseEntity<BrandResponse> create(@Valid @RequestBody BrandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.create(request));
    }

    /**
     * Recupera el listado completo de marcas disponibles.
     *
     * @return 200 OK con la lista de marcas.
     */
    @GetMapping
    public ResponseEntity<List<BrandResponse>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }

    /**
     * Busca una marca específica por su identificador.
     *
     * @param id Identificador de la marca.
     * @return 200 OK si se encuentra, o error gestionado (404) si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    /**
     * Actualiza la información de una marca existente.
     *
     * @param id Identificador de la marca a modificar.
     * @param request Datos nuevos para la actualización.
     * @return 200 OK con la marca actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequest request
    ) {
        return ResponseEntity.ok(brandService.update(id, request));
    }

    /**
     * Elimina una marca del sistema.
     *
     * @param id Identificador de la marca a eliminar.
     * @return 204 No Content indicando que la operación fue exitosa y no hay contenido que devolver.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}