package com.example.usuarios.service;


import com.example.usuarios.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    //Metodos de cada parametro
    List<User> getAllUser(); //Listar todos los usuarios
    Optional<User> getUserById(Long id);//Listar usuario por id
    User createUser(User user);//Crear usuario
    User  updateUser(Long id, User user );//Actualizar usuario
    void deleteUser(Long id);//Eliminar usuario

    String login(String email, String clave);//Login usuario
}

