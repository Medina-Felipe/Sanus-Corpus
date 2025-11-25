package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.Role;
import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository;
import com.backendpill.shared.domain.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Usamos MockitoExtension para inicializar los Mocks automáticamente sin setup manual
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 1. Mocks: Los actores falsos que simulan el comportamiento externo
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    // 2. InjectMocks: Instancia real del servicio con los mocks inyectados dentro
    @InjectMocks
    private UserService userService;

    // --- TEST: loadUserByUsername ---

    @Test
    @DisplayName("Debe retornar UserDetails cuando el usuario existe")
    void loadUserByUsername_Success() {
        // Given
        String email = "test@ufro.cl";
        User mockUser = User.builder()
                .email(email)
                .password("encodedPass")
                .role(Role.CLIENT)
                .build();

        // Enseñamos al mock qué responder
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // When
        UserDetails result = userService.loadUserByUsername(email);

        // Then
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        verify(userRepository).findByEmail(email); // Verificamos que el repo fue llamado
    }

    @Test
    @DisplayName("Debe lanzar UsernameNotFoundException cuando el usuario no existe")
    void loadUserByUsername_NotFound() {
        // Given
        String email = "noexiste@ufro.cl";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(email);
        });
    }

    // --- TEST: register ---

    @Test
    @DisplayName("Debe registrar un usuario correctamente")
    void register_Success() {
        // Given
        UserRequest request = new UserRequest("Juan", "Perez", "juan@mail.com", "123456", "password123");

        // Simulamos que NO existe el email
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        // Simulamos el hash del password
        when(passwordEncoder.encode(request.password())).thenReturn("hashed_password123");

        // Simulamos el guardado. Ojo: save devuelve la entidad guardada
        User savedUser = User.builder()
                .id(1L) // Simulamos que la BD le asignó ID
                .email(request.email())
                .role(Role.CLIENT)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Simulamos el mapper
        UserResponse expectedResponse = new UserResponse(1L, "Juan", "Perez", "juan@mail.com", "12345678", Role.CLIENT);
        when(userMapper.toUserResponse(savedUser)).thenReturn(expectedResponse);

        // When
        UserResponse response = userService.register(request);

        // Then
        assertNotNull(response);
        assertEquals("juan@mail.com", response.email());
        verify(userRepository).save(any(User.class)); // Importante: verificar que se intentó guardar
    }

    @Test
    @DisplayName("Debe lanzar BusinessException si el email ya está en uso")
    void register_EmailAlreadyExists() {
        // Given
        UserRequest request = new UserRequest("Juan", "Perez", "duplicado@mail.com", "123", "pass");

        // Simulamos que YA existe
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.register(request);
        });

        assertEquals("El email duplicado@mail.com ya está en uso.", exception.getMessage());

        // Verificación crítica de arquitecto: Asegurar que NUNCA se llamó a save() si falló la validación
        verify(userRepository, never()).save(any());
    }

    // --- TEST: findByEmailAsResponse ---

    @Test
    @DisplayName("Debe retornar UserResponse buscando por email")
    void findByEmailAsResponse_Success() {
        // Given
        String email = "existente@mail.com";
        User user = User.builder().email(email).build();
        UserResponse userResponse = new UserResponse(
                1L,                   // 1. ID
                "Nombre",             // 2. Nombre
                "ApellidoTest",       // 3. Apellido (ESTE FALTABA)
                email,                // 4. Email
                "123456789",          // 5. Teléfono (ESTE TAMBIÉN FALTABA)
                Role.CLIENT       // CORRECTO: Esto devuelve el objeto Role que espera el Record    // 6. Rol (Como String)
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        // When
        UserResponse result = userService.findByEmailAsResponse(email);

        // Then
        assertEquals(email, result.email());
    }

    @Test
    @DisplayName("Debe lanzar excepción si busca por email y no existe (para response)")
    void findByEmailAsResponse_NotFound() {
        // Given
        String email = "fantasma@mail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () ->
                userService.findByEmailAsResponse(email)
        );
    }
}