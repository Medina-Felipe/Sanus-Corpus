package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.Role;
import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository;
import com.backendpill.shared.domain.BusinessException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para la integridad de datos

import java.util.List;

@Service
@Transactional(readOnly = true) // 1. Por defecto, todas las operaciones son de solo lectura (optimización)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    // --- SPRING SECURITY ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    // --- REGISTRO ---
    @Transactional // 2. Habilitamos escritura para este método
    public UserResponse register(UserRequest request) {
        // Validación de unicidad
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("El email " + request.email() + " ya está en uso.");
        }

        // Encriptar contraseña
        String hashedPassword = passwordEncoder.encode(request.password());

        // Construcción del usuario
        // Nota: Podrías mover esto al Mapper (userMapper.toEntity), pero hacerlo aquí está bien para ser explícito con el Rol y Password
        User newUser = User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(hashedPassword)
                .role(Role.CLIENT) // Por defecto siempre CLIENT al registrarse
                .build();

        User savedUser = userRepository.save(newUser);
        return userMapper.toUserResponse(savedUser);
    }

    // --- LECTURA ---

    public UserResponse findByEmailAsResponse(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        return userMapper.toUserResponse(user);
    }

    // Método necesario para el UserController.findAll()
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Método necesario para el UserController.findById()
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id));
        return userMapper.toUserResponse(user);
    }

    // --- ACTUALIZACIÓN ---

    @Transactional // Habilitamos escritura
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id));

        // Validación de cambio de email:
        // Si el email que viene en el request es diferente al que ya tiene el usuario...
        if (!request.email().equals(user.getEmail())) {
            // ... verificamos que ese nuevo email no esté siendo usado por OTRA persona.
            userRepository.findByEmail(request.email()).ifPresent(u -> {
                throw new BusinessException("El email " + request.email() + " ya está ocupado por otro usuario.");
            });
            user.setEmail(request.email());
        }

        // Actualizamos campos básicos
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());

        // IMPORTANTE DE ARQUITECTO:
        // NO actualizamos la contraseña aquí. request.password() se ignora deliberadamente.
        // Cambiar contraseña es un proceso sensible que merece su propio endpoint (changePassword).

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    // --- ELIMINACIÓN ---

    @Transactional // Habilitamos escritura
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}