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

/**
 * Servicio de dominio responsable de la gestión integral de usuarios.
 * <p>
 * Maneja el ciclo de vida de los usuarios (CRUD), la encriptación de credenciales
 * y la aplicación de reglas de negocio como unicidad de emails.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en la base de datos aplicando reglas de negocio.
     *
     * @param request DTO con los datos del usuario.
     * @return El usuario registrado en formato DTO.
     * @throws BusinessException Si el correo electrónico ya está en uso.
     */
    @Transactional
    public UserResponse register(UserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("El email ya está registrado");
        }

        User newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(Role.CLIENT);

        User savedUser = userRepository.save(newUser);
        return userMapper.toUserResponse(savedUser);
    }

    /**
     * Busca un usuario por email y lo retorna en formato de respuesta.
     *
     * @param email Email a buscar.
     * @return DTO del usuario encontrado.
     * @throws BusinessException Si el usuario no existe.
     */
    @Transactional(readOnly = true)
    public UserResponse findByEmailAsResponse(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }

    /**
     * Recupera la lista completa de usuarios del sistema.
     *
     * @return Lista de DTOs de usuarios.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return DTO del usuario encontrado.
     * @throws BusinessException Si el usuario no existe.
     */
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }

    /**
     * Actualiza la información personal de un usuario.
     *
     * @param id ID del usuario a modificar.
     * @param request DTO con los nuevos datos.
     * @return El usuario actualizado en formato DTO.
     * @throws BusinessException Si el usuario no existe.
     */
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        userMapper.updateUserFromDto(request, user);
        // Nota: La actualización de contraseña requeriría un endpoint separado y re-encriptación.

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar.
     * @throws BusinessException Si el usuario no existe.
     */
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}