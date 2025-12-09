package com.backendpill.support.domain;

/**
 * Define el ciclo de vida de un ticket de soporte (Máquina de Estados).
 * Permite rastrear el progreso de la resolución de incidencias.
 */
public enum TicketStatus {
    /** El ticket ha sido creado y está en cola de espera para ser asignado. */
    OPEN,

    /** Un agente de soporte ha tomado el ticket y está trabajando en él. */
    IN_PROGRESS,

    /** El agente ha propuesto una solución o ha resuelto el problema técnico. */
    RESOLVED,

    /** El ticket ha sido finalizado y archivado. No admite más cambios. */
    CLOSED
}