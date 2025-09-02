package com.backendpill.catalog.application.DTOs;

public record ProductDTO(
        String name,
        double price,
        String description
) {
}
