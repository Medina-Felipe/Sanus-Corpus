package com.backendpill.catalog.application.dtos;

public record BrandResponse(
        Long id,
        String name,
        String description
) {}