package com.backendpill.support.application.DTOs;

import java.time.LocalDateTime;

public record SupportResponse(
         Long id,
         String title,
         String description,
         String status,
         LocalDateTime created
) {
}
