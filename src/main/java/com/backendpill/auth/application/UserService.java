package com.backendpill.auth.application;

// Tus otros imports (UserRepository, DTOs, etc.)
import com.backendpill.auth.domain.repository.UserRepository;
import com.backendpill.auth.application.DTOs.UserRequest;
import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.domain.Role;
import com.backendpill.auth.domain.User;
import com.backendpill.shared.domain.BusinessException;
import org.springframework.context.annotation.Lazy; // <-- 1. AÑADIR ESTA LÍNEA DE IMPORTACIÓN
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, // 3. @Lazy se queda
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    // --- ESTE ES EL MÉTODO QUE SPRING SECURITY BUSCARÁ ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    // --- MÉTODO DE REGISTRO ---
    public UserResponse register(UserRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new BusinessException("El email " + request.email() + " ya está en uso.");
        });

        String hashedPassword = passwordEncoder.encode(request.password());

        User newUser = User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(hashedPassword)
                .role(Role.CLIENT)
                .build();

        User savedUser = userRepository.save(newUser);
        return userMapper.toUserResponse(savedUser);
    }

    // --- MÉTODO DE BÚSQUEDA ---
    public UserResponse findByEmailAsResponse(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return userMapper.toUserResponse(user);
    }
}