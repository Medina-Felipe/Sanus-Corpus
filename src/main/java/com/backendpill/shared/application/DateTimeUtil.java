package com.backendpill.shared.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    // Constante para el formato estándar de fecha en toda la app
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STANDARD_FORMAT);

    // Constructor privado para evitar instanciación
    private DateTimeUtil() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada");
    }

    public static String now() {
        return LocalDateTime.now().format(formatter);
    }

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(formatter);
    }
}