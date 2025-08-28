package com.backendpill.auth.application.DTOs;

public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String role
) {
}
