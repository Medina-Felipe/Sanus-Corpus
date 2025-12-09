package com.backendpill.auth.domain;

/**
 * Define los roles de autorización disponibles en el sistema.
 * Estos roles determinan el nivel de acceso a los recursos protegidos.
 */
public enum Role {
    /** Usuario final con acceso a funcionalidades básicas de compra. */
    CLIENT,
    /** Administrador con acceso total a la gestión del sistema. */
    ADMIN,
    /** Personal de soporte con acceso a gestión de incidencias. */
    SUPPORT
}