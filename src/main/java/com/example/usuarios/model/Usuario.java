package com.example.usuarios.model;


import jakarta.persistence.*; // Librería JPA (maneja las entidades y mapeo a tablas)

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Genera getters, setters

@Entity
@Table(name = "USUARIO") // nombre de la tabla
public class Usuario  {

    @Id // clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se genera automaticamente el id
    private Long id;

    @NotBlank// valida que no este vacio
    @NotNull(message = "El nombre no puede ser nulo")// valida que el nombre no sea nulo
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")// valida que el nombre tenga entre 3 y 50 caracteres
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    @Pattern(
        regexp = ".*[A-Z].*",
        message = "La contraseña debe contener al menos una letra mayúscula"
    )
    @Pattern(
        regexp = ".*[a-z].*",
        message = "La contraseña debe contener al menos una letra minúscula"
    )
    @Pattern(
        regexp = ".*\\d.*",
        message = "La contraseña debe contener al menos un número"
    )
    @Pattern(
        regexp = ".*[!@#$%^&*(),.?\":{}|<>].*",
        message = "La contraseña debe contener al menos un carácter especial"
    )
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|USUARIO", message = "Rol inválido. Solo se permite ADMIN o USUARIO")
    private String rol;


}

