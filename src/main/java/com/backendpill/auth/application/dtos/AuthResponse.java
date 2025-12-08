package com.backendpill.auth.application.dtos;

public record AuthResponse(
        String accessToken,
        UserResponse user
) {}