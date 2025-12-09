package com.backendpill.shared.domain;

/**
 * Excepción base que representa una violación de las reglas de negocio del dominio.
 * <p>
 * Se utiliza para interrumpir el flujo de ejecución cuando una operación no puede
 * completarse debido a restricciones lógicas (ej. stock insuficiente, email duplicado).
 * <p>
 * <b>Arquitectura:</b> Esta clase es agnóstica al framework y al protocolo de transporte (HTTP).
 * No debe contener códigos de estado HTTP ni dependencias de Spring.
 */
public class BusinessException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo del error de negocio.
     *
     * @param message Descripción funcional del error.
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con un mensaje y la causa original (Wrapping).
     * Útil para no perder la traza de errores de bajo nivel (ej. SQLException)
     * al relanzarlos como errores de negocio.
     *
     * @param message Descripción funcional del error.
     * @param cause La excepción original que causó este error.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}