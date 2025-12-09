package com.backendpill.catalog.domain.repository;

import com.backendpill.catalog.domain.Product;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (Interface) para la persistencia de productos.
 * Define las operaciones disponibles para el dominio, desacoplado de la implementación (JPA/SQL).
 */
public interface ProductRepository {

    /**
     * Persiste o actualiza un producto.
     * @param product Entidad a guardar.
     * @return El producto guardado.
     */
    Product save(Product product);

    /**
     * Busca un producto por su ID interno.
     * @param id Identificador primario.
     * @return Optional con el producto si existe.
     */
    Optional<Product> findById(Long id);

    /**
     * Recupera todos los productos.
     * @return Lista completa.
     */
    List<Product> findAll();

    /**
     * Elimina físicamente un producto por su ID.
     * @param id Identificador del producto.
     */
    void deleteById(Long id);

    /**
     * Busca un producto por su slug (URL amigable).
     * @param slug Cadena única identificadora.
     * @return Optional con el producto.
     */
    Optional<Product> findBySlug(String slug);

    /**
     * Verifica la existencia de un producto.
     * @param id Identificador a verificar.
     * @return true si existe, false si no.
     */
    boolean existsById(Long id);
}