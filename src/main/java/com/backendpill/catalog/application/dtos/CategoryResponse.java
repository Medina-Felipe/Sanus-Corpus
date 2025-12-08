package com.backendpill.catalog.application.dtos;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}