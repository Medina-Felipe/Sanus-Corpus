package com.backendpill.catalog.domain;

/**
 * Clasificación legal y comercial de los medicamentos.
 * <p>
 * Define la tipología del producto farmacéutico, lo cual puede implicar
 * reglas de negocio diferenciadas (ej. los bioequivalentes pueden requerir
 * validaciones adicionales o etiquetas especiales en el frontend).
 */
public enum TypeMedicine {
    /** Medicamento identificado por su principio activo, sin marca comercial. */
    GENERIC,

    /** Medicamento comercializado bajo una marca registrada por un laboratorio. */
    BRANDED,

    /** Medicamento genérico que ha demostrado equivalencia terapéutica con el original. */
    BIOEQUIVALENT,

    /** Medicamento con estudios de equivalencia (categoría regulatoria específica). */
    W_EQUIVALENT,

    /** Otros productos de farmacia (cosmética, cuidado personal, etc.). */
    OTHER
}