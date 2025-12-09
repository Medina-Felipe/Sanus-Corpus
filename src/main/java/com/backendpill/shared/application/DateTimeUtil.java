package com.backendpill.shared.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase de utilidad para la manipulación y formateo de fechas y horas.
 * <p>
 * <b>Diseño:</b> Clase {@code final} con constructor privado para evitar instanciación
 * y herencia. Centraliza el formato de fecha para garantizar consistencia en toda la aplicación.
 */
public final class DateTimeUtil {

    /** Formato ISO extendido modificado para lectura humana (yyyy-MM-dd HH:mm:ss). */
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** Formateador thread-safe (a diferencia del antiguo SimpleDateFormat). */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STANDARD_FORMAT);

    // Constructor privado: Patrón estándar para clases de utilidad.
    private DateTimeUtil() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada");
    }

    /**
     * Obtiene la fecha y hora actual del sistema formateada como String.
     * @return Fecha actual formateada.
     */
    public static String now() {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Formatea un objeto {@link LocalDateTime} al estándar de la aplicación.
     *
     * @param dateTime La fecha a formatear.
     * @return La fecha como String, o cadena vacía si el input es null.
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(formatter);
    }
}