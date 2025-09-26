package com.backendpill.auth.infrastructure;


import com.backendpill.auth.application.DTOs.UserResponse;
import com.backendpill.auth.application.UserService;
import com.backendpill.auth.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findByLastName")
    public UserResponse findByLastName(String lastName){
        return userService.findByLastName(lastName);
    }

    @GetMapping("/findByEmail")
    public UserResponse findByEmail(String email){
        return userService.findByEmailAsResponse(email);
    }
}
