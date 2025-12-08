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

@RestController
@RequestMapping("/api/v1/legal")
@RequiredArgsConstructor
public class LegalController {

    private final LegalService legalService;

    // Crear o Actualizar (Idempotente por tipo)
    @PostMapping
    public ResponseEntity<LegalDocumentResponse> saveOrUpdate(@Valid @RequestBody LegalDocumentRequest request) {
        return ResponseEntity.ok(legalService.saveOrUpdate(request));
    }

    @GetMapping("/{type}")
    public ResponseEntity<LegalDocumentResponse> getByType(@PathVariable DocumentType type) {
        return ResponseEntity.ok(legalService.findByType(type));
    }

    @GetMapping
    public ResponseEntity<List<LegalDocumentResponse>> getAll() {
        return ResponseEntity.ok(legalService.findAll());
    }
}