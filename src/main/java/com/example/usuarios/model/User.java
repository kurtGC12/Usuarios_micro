package com.example.usuarios.model;


import jakarta.persistence.*; // Librería JPA (maneja las entidades y mapeo a tablas)

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Genera getters, setters

@Entity
@Table(name = "USUARIO") // nombre de la tabla
public class User  {

    @Id // clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se genera automaticamente el id
     private Long id;

    @NotBlank// valida que no este vacio
    @NotNull(message = "El nombre no puede ser nulo")// valida que el nombre no sea nulo
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")// valida que el nombre tenga entre 3 y 50 caracteres
    private String nombre;

    @Email(message = "Debe ingresar un correo válido")// valida que el formato del email sea correcto
    private String email;

    @NotBlank 
    private String clave;

    @NotBlank 
    @Column(nullable = false)
    private String rol; 


}

