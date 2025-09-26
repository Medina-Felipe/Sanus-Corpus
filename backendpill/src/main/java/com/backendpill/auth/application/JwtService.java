package com.backendpill.auth.application;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret; // Base64 o texto plano (elige una opción en getSigningKey)

    // En MILISEGUNDOS (ej: 3600000 = 1h)
    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    /* -------------------- CREATE / SIGN (0.11.x) -------------------- */
    public String generateToken(String username, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(extraClaims)                    // 0.11.x
                .setSubject(username)                      // 0.11.x
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 0.11.x requiere alg
                .compact();
    }

    public String generateToken(String username) {
        return generateToken(username, Map.of());
    }

    /* -------------------- READ / VERIFY (0.11.x) -------------------- */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        final String username = extractUsername(token);
        return expectedUsername.equals(username) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()                 // 0.11.x
                .setSigningKey(getSigningKey())
                .setAllowedClockSkewSeconds(60)              // tolerancia opcional
                .build()
                .parseClaimsJws(clean(token))                // 0.11.x
                .getBody();
        return resolver.apply(claims);
    }

    private String clean(String token) {
        return token == null ? null : token.replaceFirst("(?i)^Bearer\\s+", "").trim();
    }

    /* -------------------- KEY -------------------- */
    private Key getSigningKey() {
        // === OPCIÓN A: SECRET EN BASE64 (recomendada) ===
        byte[] keyBytes = Decoders.BASE64.decode(secret);    // ≥32 bytes decodificados
        return Keys.hmacShaKeyFor(keyBytes);

        // === OPCIÓN B: SECRET EN TEXTO PLANO UTF-8 ===
        //return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
