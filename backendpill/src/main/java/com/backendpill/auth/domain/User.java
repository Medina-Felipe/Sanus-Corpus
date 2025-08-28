package com.backendpill.auth.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long id;
    private String name;
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
    private Role role = Role.CLIENT;
}
