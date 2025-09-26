package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.User;
import com.backendpill.auth.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsUserByEmail(userRequest.email())){
            throw new ResponseStatusException(CONFLICT, "Email ya registrado");
        }

        User user = new User();
        user.setName(userRequest.name());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }


    @Transactional
    public UserResponse findByEmailAsResponse(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
        return toResponse(user);
    }

    @Transactional
    public UserResponse findByLastName(String lastName){
        User user = userRepository.findByLastName(lastName).orElseThrow(()->new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
    return toResponse(user);
    }


    @Transactional
    public UserResponse find(String email) {
        return findByEmailAsResponse(email);
    }


    private UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getLastName(),
                u.getEmail(),
                u.getRole() != null ? u.getRole().name() : null
        );
    }
}
