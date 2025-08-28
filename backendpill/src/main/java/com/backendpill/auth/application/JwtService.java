package com.backendpill.auth.application;

import org.springframework.context.annotation.Bean;

public interface JwtService {
    /** Genera un JWT con el subject = email/username */
    String generateToken(String subject);
}
