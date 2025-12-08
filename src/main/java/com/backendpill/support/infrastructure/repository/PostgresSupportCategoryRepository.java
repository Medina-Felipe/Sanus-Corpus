package com.backendpill.support.infrastructure.repository;

import com.backendpill.support.domain.SupportCategory;
import com.backendpill.support.domain.repository.SupportCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresSupportCategoryRepository extends JpaRepository<SupportCategory, Long>, SupportCategoryRepository {
    // Todo automático gracias a JpaRepository
}