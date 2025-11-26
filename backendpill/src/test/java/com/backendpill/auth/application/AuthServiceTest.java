package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.AuthResponse;
import com.backendpill.auth.application.DTOs.LoginRequest;
import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test Unitario Puro para la Capa de Servicio (Business Logic).
 * * <p>Objetivo: Validar la orquestación de la lógica de negocio sin levantar el contexto de Spring.
 * Se enfoca en verificar que el servicio interactúe correctamente con sus colaboradores
 * (AuthenticationManager, JwtService, UserService) y maneje las excepciones adecuadamente.</p>
 */
@ExtendWith(MockitoExtension.class) // Usamos Mockito puro, sin Spring Context (Ejecución rápida en milisegundos)
class AuthServiceTest {

    // --- MOCKS DE DEPENDENCIAS ---
    // Estos son los "actores falsos" que controlamos para probar el servicio aislado.

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    // --- SUJETO DE PRUEBA ---
    // Instancia real del AuthService con los mocks inyectados dentro.
    @InjectMocks
    private AuthService authService;

    // --- DATOS DE PRUEBA COMUNES ---
    private final String EMAIL = "juan@test.com";
    private final UserResponse mockUserResponse = new UserResponse(1L, "Juan", "Perez", EMAIL, "123456", Role.CLIENT);

    /**
     * Prueba el flujo feliz del inicio de sesión.
     * <p>
     * <b>Escenario:</b> Credenciales válidas.
     * <b>Comportamiento esperado:</b>
     * 1. Llama al AuthenticationManager.
     * 2. Genera un Token JWT.
     * 3. Recupera la información del usuario.
     * 4. Retorna un AuthResponse completo.
     * </p>
     */
    @Test
    @DisplayName("Login Exitoso: Debe autenticar, generar token y devolver respuesta")
    void login_Success() {
        // Given: Preparamos el entorno para un login exitoso
        LoginRequest request = new LoginRequest(EMAIL, "pass123");

        // Simulamos que Spring Security autentica correctamente y devuelve un Principal (UserDetails)
        Authentication authMock = mock(Authentication.class);
        UserDetails userDetails = User.withUsername(EMAIL).password("secret").roles("CLIENT").build();
        when(authMock.getPrincipal()).thenReturn(userDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);

        // Simulamos el comportamiento exitoso de los servicios auxiliares
        when(jwtService.generateToken(EMAIL)).thenReturn("token-xyz");
        when(userService.findByEmailAsResponse(EMAIL)).thenReturn(mockUserResponse);

        // When: Ejecutamos la lógica de negocio
        AuthResponse response = authService.login(request);

        // Then: Verificamos el resultado y las interacciones
        assertNotNull(response);
        assertEquals("token-xyz", response.accessToken()); // Validamos que el token generado se haya incluido
        assertEquals(EMAIL, response.user().email());

        // Verificamos que el "Gerente" (Service) realmente delegó el trabajo
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(EMAIL);
    }

    /**
     * Prueba el flujo de registro de usuario nuevo.
     * <p>
     * <b>Escenario:</b> Datos de usuario válidos y email no existente.
     * <b>Comportamiento esperado:</b>
     * 1. Llama a UserService para persistir el usuario.
     * 2. Realiza un "Auto-Login" generando un token inmediatamente.
     * 3. Retorna la sesión iniciada.
     * </p>
     */
    @Test
    @DisplayName("Registro Exitoso: Debe crear usuario y generar token automático")
    void register_Success() {
        // Given: Datos para un nuevo registro
        UserRequest request = new UserRequest("Juan", "Perez", EMAIL, "123456", "pass123");

        when(userService.register(request)).thenReturn(mockUserResponse);
        when(jwtService.generateToken(EMAIL)).thenReturn("token-register-xyz");

        // When: Ejecutamos el registro
        AuthResponse response = authService.register(request);

        // Then: Validamos que obtengamos token y datos
        assertNotNull(response);
        assertEquals("token-register-xyz", response.accessToken());
        assertEquals("Juan", response.user().name());

        // Verificamos el flujo de llamadas
        verify(userService).register(request);
        verify(jwtService).generateToken(EMAIL);
    }

    /**
     * Prueba de manejo de errores en Login.
     * <p>
     * <b>Escenario:</b> Contraseña incorrecta o usuario no encontrado.
     * <b>Comportamiento esperado:</b>
     * 1. AuthenticationManager lanza BadCredentialsException.
     * 2. El servicio propaga la excepción (no la captura silenciosamente).
     * 3. IMPORTANTE: No se debe generar ningún token.
     * </p>
     */
    @Test
    @DisplayName("Login Fallido: Si AuthManager falla, el servicio debe lanzar la excepción")
    void login_Failure() {
        // Given: Credenciales incorrectas
        LoginRequest request = new LoginRequest(EMAIL, "wrongPass");

        // Simulamos que el "Portero" (Spring Security) rechaza la entrada
        when(authenticationManager.authenticate(any()))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad creds"));

        // When & Then: Esperamos que explote con la excepción correcta
        assertThrows(org.springframework.security.authentication.BadCredentialsException.class, () -> {
            authService.login(request);
        });

        // Verificación de seguridad: Aseguramos que NUNCA se generó un token si falló el login
        verify(jwtService, never()).generateToken(anyString());
    }
}