package com.backendpill.auth.infrastructure;

import com.backendpill.auth.application.AuthService;
import com.backendpill.auth.application.DTOs.AuthResponse;
import com.backendpill.auth.application.DTOs.LoginRequest;
import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.application.JwtService;
import com.backendpill.auth.application.UserService;
import com.backendpill.auth.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test de Integración de la Capa Web (Slice Test).
 * Verifica que el Controlador exponga los endpoints correctamente y delegue
 * la lógica al Servicio de Aplicación correspondiente.
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactivamos filtros de seguridad reales para aislar el test del controlador
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulador de cliente HTTP (Postman en código)

    @Autowired
    private ObjectMapper objectMapper; // Herramienta para convertir Objetos Java <-> JSON

    // --- MOCKS DE DEPENDENCIAS ---

    /**
     * Mock del Servicio de Autenticación.
     * El controlador delegará aquí la lógica de login y registro.
     */
    @MockitoBean
    private AuthService authService;

    /**
     * Mock del Servicio de Usuarios.
     * Usado por el endpoint /me para recuperar datos del usuario.
     */
    @MockitoBean
    private UserService userService;

    /**
     * Mock de Infraestructura de Seguridad.
     * NECESARIO aunque el Controller no lo use directo, porque el JwtAuthenticationFilter
     * se levanta en el contexto de @WebMvcTest y requiere este bean para no fallar al inicio.
     */
    @MockitoBean
    private JwtService jwtService;

    // --- DATOS DE PRUEBA COMUNES ---
    private final String TEST_EMAIL = "juan@test.com";
    private final UserResponse mockUserResponse = new UserResponse(1L, "Juan", "Perez", TEST_EMAIL, "12345678", Role.CLIENT);

    /**
     * Prueba el endpoint POST /api/auth/login.
     * Escenario: Un usuario envía credenciales válidas.
     * Resultado esperado: 200 OK y un JSON con el token de acceso y datos del usuario.
     */
    @Test
    @DisplayName("POST /login - Debe delegar al AuthService y devolver accessToken")
    void login_Success() throws Exception {
        // Given: Preparamos la solicitud y la respuesta simulada del servicio
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, "password123");
        AuthResponse mockAuthResponse = new AuthResponse("fake-jwt-token", mockUserResponse);

        // Configuramos el Mock: Cuando llamen a login, devuelve esto sin preguntar
        when(authService.login(any(LoginRequest.class))).thenReturn(mockAuthResponse);

        // When & Then: Ejecutamos la petición HTTP simulada y verificamos el JSON de respuesta
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("fake-jwt-token"))
                .andExpect(jsonPath("$.user.email").value(TEST_EMAIL));
    }

    /**
     * Prueba el endpoint POST /api/auth/register.
     * Escenario: Un usuario nuevo envía sus datos de registro.
     * Resultado esperado: 200 OK (o 201 Created) y un JSON con el token (auto-login) y datos creados.
     */
    @Test
    @DisplayName("POST /register - Debe delegar al AuthService y devolver accessToken")
    void register_Success() throws Exception {
        // Given: Datos de un nuevo usuario
        UserRequest userRequest = new UserRequest("Juan", "Perez", TEST_EMAIL, "12345678", "pass123");
        AuthResponse mockAuthResponse = new AuthResponse("fake-jwt-token-register", mockUserResponse);

        // Configuramos el Mock para simular un registro exitoso
        when(authService.register(any(UserRequest.class))).thenReturn(mockAuthResponse);

        // When & Then: Hacemos el POST y validamos que devuelva el token generado
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("fake-jwt-token-register"))
                .andExpect(jsonPath("$.user.name").value("Juan"));
    }

    /**
     * Prueba el endpoint GET /api/auth/me.
     * Escenario: Un usuario ya autenticado (con Token válido) pide sus propios datos.
     * Resultado esperado: 200 OK y el JSON con el perfil del usuario.
     */
    @Test
    @DisplayName("GET /me - Debe resolver el usuario autenticado")
    void me_Success() throws Exception {
        // Given: El servicio de usuarios está listo para responder
        // Usamos any() para ser resilientes ante argumentos nulos o inesperados en el mock
        when(userService.findByEmailAsResponse(any())).thenReturn(mockUserResponse);

        // When & Then:
        // .with(user(...)) SIMULA que la petición ya pasó por el filtro de seguridad
        // y que Spring Security ya sabe quién es el usuario ("juan@test.com").
        mockMvc.perform(get("/api/auth/me")
                        .with(user(TEST_EMAIL).password("pass").roles("CLIENT")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }
}