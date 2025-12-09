package com.backendpill.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio de utilidad para la gestión del ciclo de vida de JSON Web Tokens (JWT).
 * Maneja la generación, firma, validación y extracción de información de los tokens.
 */
@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private Key signingKey;

    /**
     * Inicializa la clave de firma criptográfica al arrancar el contexto de Spring.
     * <p>
     * Se utiliza {@code @PostConstruct} para decodificar la clave secreta una única vez
     * (Eager Initialization), mejorando el rendimiento en cada petición subsecuente.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT para un usuario específico sin claims adicionales.
     *
     * @param username El nombre de usuario (subject) para el token.
     * @return La cadena del token JWT firmado.
     */
    public String generateToken(String username) {
        return generateToken(username, Map.of());
    }

    /**
     * Genera un token JWT con claims personalizados.
     *
     * @param username El nombre de usuario (subject).
     * @param extraClaims Mapa de atributos adicionales a incluir en el payload del token.
     * @return La cadena del token JWT firmado.
     */
    public String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (subject) contenido en el token.
     *
     * @param token El token JWT.
     * @return El nombre de usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Valida si un token pertenece al usuario indicado y si aún no ha expirado.
     *
     * @param token El token JWT a validar.
     * @param expectedUsername El nombre de usuario contra el cual validar.
     * @return {@code true} si el token es válido y corresponde al usuario, {@code false} en caso contrario.
     */
    public boolean isTokenValid(String token, String expectedUsername) {
        final String username = extractUsername(token);
        return (username.equals(expectedUsername)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Método genérico para extraer información específica (Claims) del token.
     *
     * @param token El token JWT.
     * @param claimsResolver Función para procesar los Claims y obtener el tipo deseado.
     * @param <T> El tipo de dato a retornar.
     * @return El valor extraído del Claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}