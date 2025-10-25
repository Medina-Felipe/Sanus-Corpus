package com.backendpill.auth.application.DTOs;

public record AuthResponse(
        String accessToken,
        UserResponse user) {
}
