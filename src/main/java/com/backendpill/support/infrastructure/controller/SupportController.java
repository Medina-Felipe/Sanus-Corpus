package com.backendpill.support.infrastructure.controller;

import com.backendpill.auth.application.UserService;
import com.backendpill.auth.application.dtos.UserResponse;
import com.backendpill.support.application.SupportService;
import com.backendpill.support.application.dtos.SupportTicketRequest;
import com.backendpill.support.application.dtos.SupportTicketResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de tickets de soporte.
 * <p>
 * Actúa como "Driving Adapter". Su responsabilidad principal, además de exponer los endpoints,
 * es integrar el contexto de Seguridad (Auth) con el contexto de Soporte.
 * Recupera la identidad del usuario autenticado y delega la lógica de negocio al servicio.
 */
@RestController
@RequestMapping("/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;
    private final UserService userService;

    /**
     * Crea un nuevo ticket para el usuario autenticado.
     * <p>
     * <b>Integración de Arquitectura:</b>
     * 1. Obtiene el principal de seguridad (UserDetails) del contexto de Spring Security.
     * 2. Utiliza {@link UserService} para resolver el ID numérico del usuario basado en su email.
     * 3. Invoca al servicio de soporte pasando el ID resuelto, asegurando que un usuario
     * solo pueda crear tickets a su propio nombre (evita ID Spoofing).
     *
     * @param request Datos del ticket.
     * @param userDetails Detalles del usuario extraídos del Token JWT.
     * @return 200 OK con el ticket creado.
     */
    @PostMapping("/tickets")
    public ResponseEntity<SupportTicketResponse> createTicket(
            @Valid @RequestBody SupportTicketRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(supportService.createTicket(user.id(), request));
    }

    /**
     * Obtiene únicamente los tickets pertenecientes al usuario que realiza la petición.
     *
     * @param userDetails Detalles del usuario autenticado.
     * @return Lista de tickets propios.
     */
    @GetMapping("/tickets/me")
    public ResponseEntity<List<SupportTicketResponse>> getMyTickets(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(supportService.getMyTickets(user.id()));
    }

    /**
     * Obtiene el listado global de tickets del sistema.
     * <p>
     * <b>Nota de Seguridad:</b> Este endpoint debería estar protegido por roles administrativos
     * (ej. {@code @PreAuthorize("hasRole('ADMIN')")}) en un entorno de producción.
     *
     * @return Lista de todos los tickets.
     */
    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {
        return ResponseEntity.ok(supportService.getAllTickets());
    }
}