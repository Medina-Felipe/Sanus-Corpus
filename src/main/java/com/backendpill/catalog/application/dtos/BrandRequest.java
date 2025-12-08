package com.backendpill.catalog.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String description
) {}