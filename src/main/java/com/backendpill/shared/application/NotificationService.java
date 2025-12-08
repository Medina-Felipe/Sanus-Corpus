package com.backendpill.shared.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Lombok genera un logger 'log' automáticamente
public class NotificationService {

    /**
     * Simula el envío de un correo de bienvenida.
     * En el futuro, aquí conectarías JavaMailSender o SendGrid.
     */
    @Async // Idealmente, esto debería ejecutarse en un hilo separado
    public void sendWelcomeEmail(String email, String name) {
        log.info("--------------------------------------------------");
        log.info("ENVIANDO EMAIL A: {}", email);
        log.info("ASUNTO: ¡Bienvenido a BackendPill, {}!", name);
        log.info("CUERPO: Estamos felices de tenerte aquí...");
        log.info("--------------------------------------------------");
    }

    @Async
    public void sendOrderConfirmation(String email, String orderId) {
        log.info("ENVIANDO CONFIRMACIÓN DE PEDIDO {} A {}", orderId, email);
    }
}