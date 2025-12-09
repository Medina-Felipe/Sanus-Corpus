package com.backendpill.legal.domain;

/**
 * Catálogo de los tipos de documentos legales gestionados por el sistema.
 * Define la estructura contractual de la plataforma.
 */
public enum DocumentType {
    /** Términos y condiciones generales de uso del servicio. */
    TERMS_AND_CONDITIONS,

    /** Política de privacidad y manejo de datos personales. */
    PRIVACY_POLICY,

    /** Política de uso de cookies y rastreadores. */
    COOKIES_POLICY,

    /** Autorización explícita para el procesamiento de datos sensibles. */
    DATA_PROCESSING_AUTHORIZATION
}