package com.backendpill.support.domain.repository;

import com.backendpill.support.domain.SupportCategory;
import java.util.List;
import java.util.Optional;

/**
 * Puerto para la gestión del catálogo de categorías de soporte.
 */
public interface SupportCategoryRepository {

    SupportCategory save(SupportCategory category);

    Optional<SupportCategory> findById(Long id);

    List<SupportCategory> findAll();
}