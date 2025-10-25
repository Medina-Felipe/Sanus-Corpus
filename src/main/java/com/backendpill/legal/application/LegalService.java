package com.backendpill.legal.application;

import com.backendpill.legal.domain.repository.LegalRepository;
import org.springframework.stereotype.Service;

@Service
public class LegalService {

    private final LegalRepository legalRepository;

    public LegalService(LegalRepository legalRepository) {
        this.legalRepository = legalRepository;
    }
}
