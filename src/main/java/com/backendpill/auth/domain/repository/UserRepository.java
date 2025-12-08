package com.backendpill.auth.domain.repository;

import com.backendpill.auth.domain.User;
import java.util.List;
import java.util.Optional;

// Arquitectura: Esta interfaz define el contrato (Puerto).
// La implementación real la hará Spring Data en tiempo de ejecución.
public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    // Es buena práctica devolver List, pero considera Page<User> si esperas muchos usuarios.
    List<User> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);
}