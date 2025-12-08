package com.backendpill.shared.domain;

/**
 * Se lanza cuando se busca una entidad y no existe.
 * EJ: "Usuario con ID 5 no encontrado".
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    // Buena práctica: Permitir encadenar excepciones
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}