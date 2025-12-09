package com.backendpill.auth.infrastructure.controller;

import com.backendpill.auth.application.UserService;
import com.backendpill.auth.application.dtos.UserRequest;
import com.backendpill.auth.application.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la administración de usuarios.
 * <p>
 * Implementa operaciones CRUD protegidas por roles específicos.
 * Se utiliza versionado en la URL (/api/v1).
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Obtiene una lista de todos los usuarios registrados.
     * <p>
     * <b>Seguridad:</b> Requiere rol 'ADMIN'.
     *
     * @return Lista de usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Busca un usuario específico por su ID.
     *
     * @param id ID del usuario.
     * @return El usuario encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param id ID del usuario a modificar.
     * @param request Datos a actualizar.
     * @return El usuario actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    /**
     * Elimina un usuario del sistema (Soft delete o Hard delete según implementación del servicio).
     * <p>
     * <b>Seguridad:</b> Requiere rol 'ADMIN'.
     *
     * @param id ID del usuario a eliminar.
     * @return Respuesta sin contenido (204 No Content).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}