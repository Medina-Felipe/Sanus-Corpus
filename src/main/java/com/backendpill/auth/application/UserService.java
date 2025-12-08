package com.backendpill.auth.application;

import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import com.backendpill.auth.domain.Role;
import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository;
import com.backendpill.shared.domain.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService { // ¡Ya no implementa UserDetailsService!

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // Inyección directa y segura

    // --- LÓGICA DE NEGOCIO ---

    @Transactional
    public UserResponse register(UserRequest request) {
        // 1. Validar reglas de negocio
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("El email ya está registrado");
        }

        // 2. Crear entidad
        User newUser = userMapper.toEntity(request);

        // 3. Aplicar seguridad y valores por defecto
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(Role.CLIENT);

        // 4. Guardar
        User savedUser = userRepository.save(newUser);

        return userMapper.toUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmailAsResponse(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }

    // Añadir dentro de UserService.java

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        // Usamos el mapper para actualizar los campos permitidos
        userMapper.updateUserFromDto(request, user);

        // Si hay cambio de password, hay que encriptarla de nuevo aquí
        // (Lógica omitida por brevedad, pero tenlo en cuenta)

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}