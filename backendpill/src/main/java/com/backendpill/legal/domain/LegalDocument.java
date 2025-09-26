package com.backendpill.legal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalDocument {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    private String content;
    private LocalDateTime created;
}
