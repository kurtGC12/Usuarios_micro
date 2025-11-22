package com.example.usuarios.repository;

import com.example.usuarios.model.Usuario;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

   //Acceso a la base de datos mediante JPA
    public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
        
        // Método personalizado para buscar usuarios por nombre
        List<Usuario> findByNombre(String nombre);
        // Método personalizado para buscar usuarios por email
        Optional<Usuario> findByEmail(String email);
        // Método personalizado para buscar usuarios por rol
        List<Usuario> findByRol(String rol);
     
       
}