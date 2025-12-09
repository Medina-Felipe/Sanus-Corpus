package com.backendpill.auth.domain.repository;

import com.backendpill.auth.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la persistencia y recuperación de entidades {@link User}.
 * <p>
 * Define el contrato de acceso a datos, desacoplando la lógica de negocio
 * de la implementación específica de la base de datos.
 */
public interface UserRepository {

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param user La entidad usuario a persistir.
     * @return El usuario guardado (puede incluir cambios generados por la BD, como el ID).
     */
    User save(User user);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email El correo electrónico a buscar.
     * @return Un {@link Optional} que contiene el usuario si existe, o vacío si no.
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id El ID del usuario.
     * @return Un {@link Optional} con el usuario encontrado.
     */
    Optional<User> findById(Long id);

    /**
     * Recupera todos los usuarios registrados en el sistema.
     * <p>
     * <b>Nota de rendimiento:</b> Usar con precaución en bases de datos con gran volumen de registros.
     * Para grandes volúmenes, preferir paginación.
     *
     * @return Una lista con todos los usuarios.
     */
    List<User> findAll();

    /**
     * Verifica si existe un usuario con el ID proporcionado.
     *
     * @param id El ID a verificar.
     * @return {@code true} si el usuario existe, {@code false} en caso contrario.
     */
    boolean existsById(Long id);

    /**
     * Elimina un usuario del sistema basándose en su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    void deleteById(Long id);
}