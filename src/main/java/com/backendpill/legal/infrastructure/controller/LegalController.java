package com.backendpill.legal.infrastructure.controller;

import com.backendpill.legal.application.LegalService;
import com.backendpill.legal.application.dtos.LegalDocumentRequest;
import com.backendpill.legal.application.dtos.LegalDocumentResponse;
import com.backendpill.legal.domain.DocumentType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la administración y consulta de documentos legales.
 * <p>
 * Actúa como un "Driving Adapter" exponiendo la lógica de negocio a través de HTTP.
 * Permite a los administradores publicar nuevas versiones de contratos y a los clientes
 * consultar los términos vigentes.
 */
@RestController
@RequestMapping("/api/v1/legal")
@RequiredArgsConstructor
public class LegalController {

    private final LegalService legalService;

    /**
     * Publica o actualiza un documento legal (Operación Upsert).
     * <p>
     * Este endpoint es idempotente respecto al tipo de documento:
     * <ul>
     * <li>Si no existe el tipo (ej. PRIVACY_POLICY), lo crea (v0).</li>
     * <li>Si ya existe, actualiza su contenido e incrementa la versión (v+1).</li>
     * </ul>
     *
     * @param request DTO con el tipo de documento y el contenido nuevo.
     * @return 200 OK con el documento actualizado/creado.
     */
    @PostMapping
    public ResponseEntity<LegalDocumentResponse> saveOrUpdate(@Valid @RequestBody LegalDocumentRequest request) {
        return ResponseEntity.ok(legalService.saveOrUpdate(request));
    }

    /**
     * Obtiene el documento legal vigente para un tipo específico.
     * <p>
     * Útil para mostrar textos legales específicos en el frontend (ej. en el footer o en el registro).
     *
     * @param type El tipo de documento (Enum) pasado como variable en la URL.
     * @return 200 OK con el documento encontrado.
     */
    @GetMapping("/{type}")
    public ResponseEntity<LegalDocumentResponse> getByType(@PathVariable DocumentType type) {
        return ResponseEntity.ok(legalService.findByType(type));
    }

    /**
     * Recupera el listado completo de todos los documentos legales del sistema.
     *
     * @return 200 OK con la lista de documentos.
     */
    @GetMapping
    public ResponseEntity<List<LegalDocumentResponse>> getAll() {
        return ResponseEntity.ok(legalService.findAll());
    }
}