package com.backendpill.shared.domain;

/**
 * Se lanza cuando el usuario intenta hacer algo sin permisos.
 * EJ: "Token inválido" o "No tienes rol de ADMIN".
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}