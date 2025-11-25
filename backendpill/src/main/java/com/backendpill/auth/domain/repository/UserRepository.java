package com.backendpill.auth.domain.repository;

import com.backendpill.auth.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Este es el PUERTO del dominio.
 * Define el contrato que la capa de aplicación necesita.
 * Al agregar estos métodos, permitimos que el UserService
 * realice operaciones CRUD completas.
 */
public interface UserRepository {

    // --- ESCRITURA ---
    User save(User user);

    // --- LECTURA ---
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id); // <-- Necesario para getUserById y update

    List<User> findAll();             // <-- Necesario para getAllUsers

    // --- UTILITARIOS ---
    boolean existsById(Long id);      // <-- Necesario para validar antes de borrar

    // --- ELIMINACIÓN ---
    void deleteById(Long id);         // <-- Necesario para deleteUser
}