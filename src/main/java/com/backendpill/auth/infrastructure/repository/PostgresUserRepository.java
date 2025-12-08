package com.backendpill.auth.infrastructure.repository;

import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresUserRepository extends JpaRepository<User, Long>, UserRepository {

    // Spring Data implementa automáticamente 'save', 'findById', 'findAll', etc.
    // porque coinciden con las firmas de JpaRepository.

    // Declaramos explícitamente este query method derivado
    // para cumplir con el contrato de findByEmail del puerto.
    @Override
    Optional<User> findByEmail(String email);
}