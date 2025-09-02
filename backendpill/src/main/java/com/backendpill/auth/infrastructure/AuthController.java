package com.backendpill.auth.infrastructure;

import com.backendpill.auth.application.DTOs.*;
import com.backendpill.auth.application.JwtService;
import com.backendpill.auth.application.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    //private final AuthenticationManager authenticationManager;
   // private final JwtService jwtService;
   // private final UserService userService;


    /*public AuthController(AuthenticationManager authenticationManager,JwtService jwtService*//*,UserService userService) {
        //this.authenticationManager = authenticationManager;
        //this.jwtService = jwtService;
        this.userService = userService;
    }
    */

    /*@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails principal = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(principal.getUsername());
        UserResponse user = userService.findByEmailAsResponse(principal.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, user));
    }*/

   /* @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        UserResponse created = userService.register(request);
        // Autologin: emitimos token directo para el nuevo usuario
        String token = jwtService.generateToken(created.email());
        return ResponseEntity.ok(new AuthResponse(token, created));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal(expression = "username") String email) {
        return ResponseEntity.ok(userService.findByEmailAsResponse(email));
    }*/
}
