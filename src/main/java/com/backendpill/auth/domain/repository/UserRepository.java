package com.backendpill.auth.domain.repository;

import com.backendpill.auth.domain.User;
import java.util.Optional;

/**
 * Este es el PUERTO del dominio.
 * Define el contrato que la capa de aplicación necesita,
 * sin saber nada sobre bases de datos o Spring.
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    // Aquí puedes agregar otros métodos de negocio puros, ej:
    // Optional<User> findById(Long id);
    // void delete(User user);
}