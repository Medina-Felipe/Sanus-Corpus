package com.backendpill.support.domain;

/**
 * Nivel de urgencia o impacto del ticket.
 * <p>
 * Se utiliza para determinar los SLAs (Service Level Agreements) y el orden
 * de atención en la cola de soporte.
 */
public enum TicketPriority {
    /** Consultas generales, dudas o sugerencias sin impacto operativo. */
    LOW,

    /** Problemas que afectan parcialmente la funcionalidad pero permiten seguir operando. */
    MEDIUM,

    /** Errores que bloquean funcionalidades críticas o flujos de venta. */
    HIGH,

    /** Caída total del sistema, pérdida de datos o problemas de seguridad críticos. */
    URGENT
}