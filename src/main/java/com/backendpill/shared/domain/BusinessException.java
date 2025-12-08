package com.backendpill.shared.domain;

/**
 * Excepción base para errores de lógica de negocio.
 * EJ: "El stock no es suficiente", "El email ya existe".
 * * ARQUITECTURA:
 * Esta clase NO debe saber nada de HTTP ni de Spring.
 * Es agnóstica al framework.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}