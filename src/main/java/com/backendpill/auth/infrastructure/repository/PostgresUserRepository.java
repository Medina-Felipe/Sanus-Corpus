package com.backendpill.auth.infrastructure.repository;

import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository; // 1. Implementa el puerto del Dominio
import org.springframework.data.jpa.repository.JpaRepository;  // 2. Extiende la magia de Spring Data
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Este es el ADAPTADOR de infraestructura.
 * Conecta el mundo de Spring Data JPA con el contrato
 * de nuestro dominio (UserRepository).
 */
@Repository
public interface PostgresUserRepository extends JpaRepository<User, Long>, UserRepository {

    // Spring Data implementará "save(User user)" automáticamente
    // porque ya viene en JpaRepository y coincide con la firma
    // de nuestro UserRepository.

    // Debemos declarar este método para que Spring Data lo implemente
    // (basado en el nombre) y para cumplir el contrato de UserRepository.
    @Override
    Optional<User> findByEmail(String email);
}