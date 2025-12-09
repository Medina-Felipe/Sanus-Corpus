package com.backendpill.auth.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación o actualización de usuarios.
 * Define las reglas de validación de entrada para garantizar la integridad de los datos
 * antes de que lleguen a la capa de dominio.
 *
 * @param name Nombre de pila del usuario.
 * @param lastName Apellido del usuario.
 * @param email Correo electrónico único.
 * @param password Contraseña del usuario. Requiere una longitud mínima por seguridad.
 * @param phoneNumber Número de contacto (opcional).
 */
public record UserRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        String phoneNumber
) {}