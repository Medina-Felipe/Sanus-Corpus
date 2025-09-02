package com.backendpill.auth.application;

import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.User;
import com.backendpill.auth.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse create(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.name());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());

        User saved = userRepository.save(user);
        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getRole() != null ? saved.getRole().name() : null
        );
    }


}
