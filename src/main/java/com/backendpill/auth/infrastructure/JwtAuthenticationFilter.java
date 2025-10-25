package com.backendpill.auth.infrastructure;

import com.backendpill.auth.application.JwtService;
import com.backendpill.auth.application.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Es un bean de Spring
@RequiredArgsConstructor // Inyecta las dependencias por constructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // 1. Si no hay header o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraemos el token (quitando "Bearer ")
        final String token = authHeader.substring(7);
        final String userEmail;

        try {
            userEmail = jwtService.extractUsername(token);
        } catch (Exception e) {
            // Si el token está expirado o es inválido, no hacemos nada y dejamos que
            // el resto de filtros de Spring Security lo rechacen.
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Si tenemos email y el usuario no está autenticado en el contexto actual...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 4. Cargamos el usuario desde la BD
            UserDetails userDetails = this.userService.loadUserByUsername(userEmail);

            // 5. Validamos el token contra los datos del usuario
            if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                // 6. Si es válido, creamos la autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No necesitamos credenciales (password) aquí
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Seteamos la autenticación en el Contexto de Seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Pasamos al siguiente filtro
        filterChain.doFilter(request, response);
    }
}