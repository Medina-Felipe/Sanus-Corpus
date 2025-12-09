package com.backendpill.shared.domain;

/**
 * Excepción de seguridad lanzada cuando se deniega el acceso a una operación.
 * <p>
 * Puede deberse a:
 * <ul>
 * <li>Falta de autenticación (usuario anónimo).</li>
 * <li>Falta de autorización (usuario autenticado pero sin el Rol necesario).</li>
 * </ul>
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Crea la excepción con el motivo del rechazo de acceso.
     *
     * @param message Explicación de por qué se deniega el permiso.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Crea la excepción envolviendo una causa subyacente.
     *
     * @param message Explicación de por qué se deniega el permiso.
     * @param cause Excepción original de seguridad (si existe).
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}