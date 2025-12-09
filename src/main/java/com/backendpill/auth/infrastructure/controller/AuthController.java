package com.backendpill.auth.infrastructure.controller;

import com.backendpill.auth.application.AuthService;
import com.backendpill.auth.application.UserService;
import com.backendpill.auth.application.dtos.AuthResponse;
import com.backendpill.auth.application.dtos.LoginRequest;
import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controlador REST para la gestión de la autenticación y el registro de usuarios.
 * Expone endpoints públicos para obtener acceso al sistema.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request DTO con la información de registro.
     * @return Respuesta con el token de acceso y datos del usuario creado.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Inicia sesión para un usuario existente.
     *
     * @param request Credenciales de acceso (email y password).
     * @return Respuesta con el token de acceso JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Obtiene la información del perfil del usuario autenticado actualmente.
     * <p>
     * Utiliza el objeto {@link Principal} inyectado por Spring Security, que contiene
     * el identificador (email) del usuario extraído del token JWT.
     *
     * @param principal Identidad del usuario autenticado.
     * @return Detalles del perfil del usuario.
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Principal principal) {
        return ResponseEntity.ok(userService.findByEmailAsResponse(principal.getName()));
    }
}