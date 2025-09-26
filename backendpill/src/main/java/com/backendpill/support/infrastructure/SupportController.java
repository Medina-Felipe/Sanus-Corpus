package com.backendpill.support.infrastructure;

import com.backendpill.support.application.SupportService;
import com.backendpill.support.domain.SupportTicket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping("/id")
    public Optional<SupportTicket> findById(Long id){
        return supportService.findById(id);
    }

    @GetMapping("/title")
    public Optional<SupportTicket> findByTitle(String title){
        return supportService.findByTitle(title);
    }

    @GetMapping("/description")
    public Optional<SupportTicket> findByDescription(String description){
        return supportService.findByDescription(description);
    }

    @GetMapping("/status")
    public Optional<SupportTicket> findByStatus(String status){
        return supportService.findByStatus(status);
    }

    @GetMapping("/created")
    public Optional<SupportTicket> findByCreated(LocalDateTime created){
        return supportService.findByCreated(created);
    }
}
