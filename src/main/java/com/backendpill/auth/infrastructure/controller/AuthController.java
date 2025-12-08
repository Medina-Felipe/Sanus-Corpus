package com.backendpill.auth.infrastructure.controller;

import com.backendpill.auth.application.AuthService;
import com.backendpill.auth.application.UserService; // Este UserService es solo para datos, no auth
import com.backendpill.auth.application.dtos.AuthResponse;
import com.backendpill.auth.application.dtos.LoginRequest;
import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // endpoint "/me" optimizado: usamos Principal en lugar de AuthenticationPrincipal complejo
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Principal principal) {
        return ResponseEntity.ok(userService.findByEmailAsResponse(principal.getName()));
    }
}