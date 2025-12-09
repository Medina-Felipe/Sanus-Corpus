package com.backendpill.auth.infrastructure.repository;

import com.backendpill.auth.domain.User;
import com.backendpill.auth.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementación de persistencia para la entidad Usuario utilizando Spring Data JPA.
 * <p>
 * Esta interfaz actúa como un adaptador de persistencia: extiende {@link JpaRepository}
 * para obtener la funcionalidad de Spring, e implementa {@link UserRepository}
 * para cumplir con el contrato definido en la capa de dominio.
 */
@Repository
public interface PostgresUserRepository extends JpaRepository<User, Long>, UserRepository {

    /**
     * Busca un usuario por email.
     * <p>
     * Implementación automática mediante Query Method de Spring Data JPA.
     * Cumple con el contrato de la interfaz de dominio.
     *
     * @param email Email del usuario.
     * @return Optional conteniendo el usuario si existe.
     */
    @Override
    Optional<User> findByEmail(String email);
}