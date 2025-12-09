package com.backendpill.shared.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envoltorio estandarizado para todas las respuestas de la API (Envelope Pattern).
 * <p>
 * Garantiza que el cliente (Frontend) siempre reciba una estructura predecible,
 * independientemente de si la operación fue exitosa o fallida.
 *
 * @param <T> El tipo de dato del payload (contenido) que se devuelve.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /** Indica si la solicitud se procesó correctamente (true) o si hubo un error (false). */
    private boolean success;

    /** Mensaje descriptivo para el usuario o desarrollador (ej. "Usuario creado", "Error de validación"). */
    private String message;

    /** El objeto de negocio retornado. Puede ser nulo en caso de error. */
    private T data;

    // --- MÉTODOS DE FÁBRICA (Static Factory Methods) ---

    /**
     * Crea una respuesta exitosa con datos.
     *
     * @param data El objeto a devolver.
     * @param <T> El tipo del objeto.
     * @return Instancia de ApiResponse configurada como exitosa.
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Operación exitosa")
                .data(data)
                .build();
    }

    /**
     * Crea una respuesta exitosa con datos y un mensaje personalizado.
     *
     * @param data El objeto a devolver.
     * @param message Mensaje personalizado.
     * @param <T> El tipo del objeto.
     * @return Instancia de ApiResponse.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Crea una respuesta de error con datos parciales o de depuración.
     *
     * @param message Descripción del error.
     * @param data Datos contextuales del error.
     * @param <T> El tipo de dato.
     * @return Instancia de ApiResponse configurada como fallida.
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Crea una respuesta de error simple sin datos adjuntos.
     *
     * @param message Descripción del error.
     * @param <T> El tipo genérico (infeido).
     * @return Instancia de ApiResponse.
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}