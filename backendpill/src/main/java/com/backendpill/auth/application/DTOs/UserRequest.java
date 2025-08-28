package com.backendpill.auth.application.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @NotBlank String password,
        String phoneNumber
) {
}
