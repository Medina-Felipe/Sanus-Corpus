package com.backendpill.support.domain.repository;

import com.backendpill.support.domain.SupportCategory;
import java.util.List;
import java.util.Optional;

public interface SupportCategoryRepository {
    SupportCategory save(SupportCategory category);
    Optional<SupportCategory> findById(Long id);
    List<SupportCategory> findAll();
}