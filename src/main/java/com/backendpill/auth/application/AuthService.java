package com.backendpill.auth.application;

import com.backendpill.auth.application.dtos.AuthResponse;
import com.backendpill.auth.application.dtos.LoginRequest;
import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Servicio de orquestación para procesos de autenticación y autorización.
 * Coordina la validación de credenciales, la generación de tokens y el registro de usuarios.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * Autentica a un usuario basándose en sus credenciales y genera un token de acceso.
     *
     * @param request DTO con email y contraseña.
     * @return Una respuesta de autenticación con el token JWT y los datos del usuario.
     * @throws BadCredentialsException Si el email o la contraseña son incorrectos.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserResponse user = userService.findByEmailAsResponse(request.email());
        String token = jwtService.generateToken(user.email());

        return new AuthResponse(token, user);
    }

    /**
     * Registra un nuevo usuario en el sistema y lo autentica automáticamente.
     *
     * @param request DTO con los datos del nuevo usuario.
     * @return Una respuesta de autenticación con el token JWT recién generado.
     */
    public AuthResponse register(UserRequest request) {
        UserResponse createdUser = userService.register(request);
        String token = jwtService.generateToken(createdUser.email());
        return new AuthResponse(token, createdUser);
    }
}