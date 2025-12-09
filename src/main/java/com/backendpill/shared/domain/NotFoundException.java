package com.backendpill.shared.domain;

/**
 * Excepción específica lanzada cuando una entidad requerida no puede ser localizada
 * en el repositorio de datos.
 * <p>
 * Diferenciar esta excepción permite a la capa de infraestructura traducir el error
 * correctamente (generalmente a un código 404 Not Found en APIs REST).
 */
public class NotFoundException extends RuntimeException {

    /**
     * Crea la excepción indicando qué recurso no se encontró.
     *
     * @param message Mensaje detallando el ID o criterio de búsqueda fallido.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Crea la excepción envolviendo una causa raíz.
     *
     * @param message Mensaje detallando el recurso no encontrado.
     * @param cause Excepción original (si aplica).
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}