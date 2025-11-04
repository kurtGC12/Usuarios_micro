package com.example.usuarios.repository;

import com.example.usuarios.model.User;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

   //Acceso a la base de datos mediante JPA
    public interface UserRepository extends JpaRepository<User, Long>{
        
        // Método personalizado para buscar usuarios por nombre
        List<User> findByNombre(String nombre);
     
     
       
}