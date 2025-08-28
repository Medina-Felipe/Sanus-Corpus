package com.backendpill.support.application;

import com.backendpill.support.infrastructure.SupportRepository;
import org.springframework.stereotype.Service;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }
}
