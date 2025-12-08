package com.backendpill.catalog.infrastructure.repository;

import com.backendpill.catalog.domain.Category;
import com.backendpill.catalog.domain.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresCategoryRepository extends JpaRepository<Category, Long>, CategoryRepository {
}