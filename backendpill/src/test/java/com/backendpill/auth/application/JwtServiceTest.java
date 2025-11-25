package com.backendpill.auth.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    // Clave de 256 bits (32 bytes) codificada en Base64 para cumplir con HS256
    // "estaEsUnaClaveSuperSecretaParaTestQueTieneMasDe32BytesDeLongitud" -> Base64
    private static final String TEST_SECRET_RAW = "estaEsUnaClaveSuperSecretaParaTestQueTieneMasDe32BytesDeLongitud";
    private String TEST_SECRET_BASE64;

    private static final long TEST_EXPIRATION_MS = 3600000; // 1 Hora

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // 1. Preparamos un secreto válido en Base64
        TEST_SECRET_BASE64 = Base64.getEncoder().encodeToString(TEST_SECRET_RAW.getBytes());

        // 2. Simulamos la inyección de @Value usando ReflectionTestUtils
        // Esto evita tener que levantar todo el contexto de Spring (@SpringBootTest)
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET_BASE64);
        ReflectionTestUtils.setField(jwtService, "expirationMs", TEST_EXPIRATION_MS);
    }

    @Test
    @DisplayName("Debe generar un token no nulo y con formato correcto")
    void generateToken() {
        // Given
        String username = "testUser";

        // When
        String token = jwtService.generateToken(username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        // Un JWT tiene 3 partes separadas por puntos (Header.Payload.Signature)
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    @DisplayName("Debe generar un token con Extra Claims")
    void testGenerateTokenWithClaims() {
        // Given
        String username = "adminUser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");

        // When
        String token = jwtService.generateToken(username, claims);

        // Then
        assertNotNull(token);
        // Verificamos extrayendo el usuario para asegurar que el token es funcional
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    @DisplayName("Debe extraer el usuario correcto del token")
    void extractUsername() {
        // Given
        String expectedUsername = "usuario_ufro";
        String token = jwtService.generateToken(expectedUsername);

        // When
        String actualUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    @DisplayName("Debe validar correctamente si el usuario coincide y el token no ha expirado")
    void isTokenValid() {
        // Given
        String username = "estudiante_ing";
        String token = jwtService.generateToken(username);

        // When
        boolean isValid = jwtService.isTokenValid(token, username);

        // Then
        assertTrue(isValid, "El token debería ser válido para el mismo usuario");
    }

    @Test
    @DisplayName("Debe fallar la validación si el usuario no coincide")
    void isTokenValid_WrongUser() {
        // Given
        String username = "usuario_real";
        String token = jwtService.generateToken(username);
        String fakeUser = "hacker";

        // When
        boolean isValid = jwtService.isTokenValid(token, fakeUser);

        // Then
        assertFalse(isValid, "El token NO debería ser válido para otro usuario");
    }

    @Test
    @DisplayName("Debe manejar correctamente el prefijo Bearer si se pasa sucio (Test de caja blanca)")
    void extractUsername_WithBearerPrefix() {
        // Tu método 'clean' limpia el token, vamos a probar si extractUsername funciona
        // simulando un token que llega crudo (aunque tu método clean es privado, lo probamos indirectamente)

        // Given
        String username = "userWithBearer";
        String rawToken = jwtService.generateToken(username);
        String tokenWithBearer = "Bearer " + rawToken;

        // When
        // Nota: Tu método extractUsername llama a extractClaim -> parseClaimsJws(clean(token))
        // Por lo tanto, debería ser capaz de limpiar el Bearer internamente.
        String extracted = jwtService.extractUsername(tokenWithBearer);

        // Then
        assertEquals(username, extracted);
    }
}