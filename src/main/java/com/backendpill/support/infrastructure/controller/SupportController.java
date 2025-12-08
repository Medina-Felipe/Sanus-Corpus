package com.backendpill.support.infrastructure.controller;

import com.backendpill.auth.application.UserService;
import com.backendpill.auth.application.dtos.UserResponse; // Importamos el DTO
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

@RestController
@RequestMapping("/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;
    private final UserService userService;
    // ELIMINADO: private final CustomUserDetailsService ... (No se necesita aquí)

    @PostMapping("/tickets")
    public ResponseEntity<SupportTicketResponse> createTicket(
            @Valid @RequestBody SupportTicketRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // ARQUITECTURA LIMPIA:
        // 1. Usamos el UserService para traducir el email (del token) a datos de usuario
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());

        // 2. Extraemos el ID y creamos el ticket
        return ResponseEntity.ok(supportService.createTicket(user.id(), request));
    }

    @GetMapping("/tickets/me")
    public ResponseEntity<List<SupportTicketResponse>> getMyTickets(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // Reutilizamos la lógica de obtención de ID
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(supportService.getMyTickets(user.id()));
    }

    // Solo para admins
    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {
        return ResponseEntity.ok(supportService.getAllTickets());
    }
}