package com.backendpill.support.application;

import com.backendpill.support.domain.SupportTicket;
import com.backendpill.support.infrastructure.SupportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Transactional
    public Optional<SupportTicket> findById(Long id){
        return supportRepository.findById(id);
    }

    @Transactional
    public Optional<SupportTicket> findByTitle(String title){
        return supportRepository.findByTitle(title);
    }

    @Transactional
    public Optional<SupportTicket> findByDescription(String description){
        return supportRepository.findByDescription(description);
    }

    @Transactional
    public Optional<SupportTicket> findByStatus(String status){
        return supportRepository.findByStatus(status);
    }

    @Transactional
    public Optional<SupportTicket> findByCreated(LocalDateTime created){
        return supportRepository.findByCreated(created);
    }
}
