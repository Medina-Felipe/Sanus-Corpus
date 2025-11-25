package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.AuthResponse;
import com.backendpill.auth.application.DTOs.LoginRequest;
import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Valida las credenciales del usuario, genera un token JWT y retorna la sesión.
     */
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails principal = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(principal.getUsername());
        UserResponse user = userService.findByEmailAsResponse(principal.getUsername());
        return new AuthResponse(token, user);
    }

    /**
     * Registra un nuevo usuario en la base de datos y realiza un auto-login inmediato.
     */
    public AuthResponse register(UserRequest request) {
        UserResponse createdUser = userService.register(request);
        String token = jwtService.generateToken(createdUser.email());
        return new AuthResponse(token, createdUser);
    }
}