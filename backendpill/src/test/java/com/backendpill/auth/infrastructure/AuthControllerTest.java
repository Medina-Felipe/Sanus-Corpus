package com.backendpill.auth.infrastructure;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    private final String TEST_EMAIL = "juan@test.com";
    private final UserResponse mockUserResponse = new UserResponse(1L, "Juan", "Perez", TEST_EMAIL, "12345678", Role.CLIENT);

    @Test
    @DisplayName("POST /login - Debe autenticar y devolver accessToken")
    void login_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, "password123");

        Authentication authMock = mock(Authentication.class);
        UserDetails userDetails = User.withUsername(TEST_EMAIL).password("irrelevant").roles("CLIENT").build();

        when(authMock.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);

        when(jwtService.generateToken(TEST_EMAIL)).thenReturn("fake-jwt-token");
        when(userService.findByEmailAsResponse(TEST_EMAIL)).thenReturn(mockUserResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("fake-jwt-token"))
                .andExpect(jsonPath("$.user.email").value(TEST_EMAIL));
    }

    @Test
    @DisplayName("POST /register - Debe registrar y devolver accessToken")
    void register_Success() throws Exception {
        UserRequest userRequest = new UserRequest("Juan", "Perez", TEST_EMAIL, "12345678", "pass123");

        when(userService.register(any(UserRequest.class))).thenReturn(mockUserResponse);
        when(jwtService.generateToken(TEST_EMAIL)).thenReturn("fake-jwt-token-register");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("fake-jwt-token-register"))
                .andExpect(jsonPath("$.user.name").value("Juan"));
    }

    @Test
    @DisplayName("GET /me - Debe resolver el usuario autenticado")
    void me_Success() throws Exception {
        when(userService.findByEmailAsResponse(any())).thenReturn(mockUserResponse);

        mockMvc.perform(get("/api/auth/me")
                        .with(user(TEST_EMAIL).password("pass").roles("CLIENT")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }
}