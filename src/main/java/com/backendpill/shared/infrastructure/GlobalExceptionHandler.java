package com.backendpill.shared.infrastructure;

import com.backendpill.shared.domain.BusinessException;
import com.backendpill.shared.domain.NotFoundException;
import com.backendpill.shared.domain.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Interceptor global de excepciones (Cross-Cutting Concern).
 * <p>
 * Utiliza el patrón <b>Controller Advice</b> para centralizar el manejo de errores
 * de toda la aplicación. Esto evita duplicar bloques try-catch en cada Controlador
 * y garantiza una estructura de respuesta de error consistente (JSON) para el cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- TRADUCCIÓN DE EXCEPCIONES DE DOMINIO A HTTP ---

    /**
     * Maneja violaciones de reglas de negocio.
     * <p>
     * <b>Mapeo:</b> {@link BusinessException} -> 400 Bad Request.
     * Indica que el cliente envió datos sintácticamente correctos, pero que violan la lógica
     * del sistema (ej. stock insuficiente, email duplicado).
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Maneja recursos no encontrados.
     * <p>
     * <b>Mapeo:</b> {@link NotFoundException} -> 404 Not Found.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Maneja errores de autorización y permisos.
     * <p>
     * <b>Mapeo:</b> {@link UnauthorizedException} -> 401 Unauthorized (o 403 Forbidden).
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    // --- VALIDACIONES DE SPRING (@Valid) ---

    /**
     * Maneja errores de validación automática de DTOs (JSR-380/Hibernate Validator).
     * <p>
     * Se dispara automáticamente cuando falla una anotación como {@code @NotNull}, {@code @Email}, etc.
     * Recopila todos los errores de campo y los devuelve en un mapa detallado.
     *
     * @param ex Excepción que contiene la lista de violaciones.
     * @return 400 Bad Request con el detalle de cada campo inválido.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de validación en los campos");
        response.put("details", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // --- ERRORES NO CONTROLADOS (CATCH-ALL) ---

    /**
     * Manejador de último recurso para cualquier excepción no prevista.
     * <p>
     * <b>Seguridad:</b> Es crítico NO devolver el {@code stackTrace} al cliente,
     * ya que podría exponer vulnerabilidades o detalles de la infraestructura.
     * Se loguea el error internamente y se devuelve un mensaje genérico.
     *
     * @param ex La excepción no controlada.
     * @return 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        // Logueamos el error real en la consola del servidor para depuración interna
        ex.printStackTrace(); // En producción usar: log.error("Error no controlado", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor. Por favor contacte al soporte.");
    }

    /**
     * Método helper para construir la estructura JSON estándar de respuesta de error.
     *
     * @param status Código HTTP.
     * @param message Mensaje legible para el usuario.
     * @return ResponseEntity construida.
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}