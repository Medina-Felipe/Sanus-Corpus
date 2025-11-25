package com.backendpill.auth.infrastructure;

import com.backendpill.auth.application.AuthService;
import com.backendpill.auth.application.DTOs.AuthResponse;
import com.backendpill.auth.application.DTOs.LoginRequest;
import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.application.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    /**
     * Inicia sesión con email y contraseña, retornando un JWT válido.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Crea una cuenta nueva y realiza un inicio de sesión automático.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Obtiene el perfil del usuario autenticado actualmente (basado en el Token).
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal(expression = "username") String email) {
        // Este endpoint es simple lectura, puede ir directo al UserService
        return ResponseEntity.ok(userService.findByEmailAsResponse(email));
    }
}