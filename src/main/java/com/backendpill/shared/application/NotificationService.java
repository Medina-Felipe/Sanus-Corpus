package com.backendpill.shared.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de notificaciones (Email, SMS, Push).
 * <p>
 * <b>Patrón Asíncrono:</b> El envío de correos es una operación costosa (I/O Bound) que depende
 * de servidores externos (SMTP). Utilizamos {@code @Async} para ejecutar estas tareas
 * en un hilo secundario, evitando bloquear la respuesta HTTP al usuario.
 */
@Service
@Slf4j
public class NotificationService {

    /**
     * Envía un correo de bienvenida al usuario registrado.
     * <p>
     * La ejecución es no-bloqueante.
     *
     * @param email Dirección de destino.
     * @param name Nombre del usuario.
     */
    @Async
    public void sendWelcomeEmail(String email, String name) {
        // Simulación del retraso de red de un servidor SMTP
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        log.info("--------------------------------------------------");
        log.info("📧 ENVIANDO EMAIL A: {}", email);
        log.info("📝 ASUNTO: ¡Bienvenido a BackendPill, {}!", name);
        log.info("📄 CUERPO: Estamos felices de tenerte aquí...");
        log.info("--------------------------------------------------");
    }

    /**
     * Notifica la confirmación de una orden de compra.
     *
     * @param email Dirección de destino.
     * @param orderId Identificador de la orden.
     */
    @Async
    public void sendOrderConfirmation(String email, String orderId) {
        log.info("📦 ENVIANDO CONFIRMACIÓN DE PEDIDO {} A {}", orderId, email);
    }
}