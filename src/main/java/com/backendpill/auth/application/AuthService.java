package com.backendpill.auth.application;

import com.backendpill.auth.application.dtos.AuthResponse;
import com.backendpill.auth.application.dtos.LoginRequest;
import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponse login(LoginRequest request) {
        // La autenticación fallará si las credenciales son malas, lanzando AuthenticationException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Si llegamos aquí, el usuario es válido.
        // Recuperamos los datos completos para la respuesta.
        UserResponse user = userService.findByEmailAsResponse(request.email());

        String token = jwtService.generateToken(user.email());
        return new AuthResponse(token, user);
    }

    public AuthResponse register(UserRequest request) {
        UserResponse createdUser = userService.register(request);
        String token = jwtService.generateToken(createdUser.email());
        return new AuthResponse(token, createdUser);
    }
}