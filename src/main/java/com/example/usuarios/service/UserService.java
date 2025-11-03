package com.example.usuarios.service;


import com.example.usuarios.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    //Metodos de cada parametro
    List<User> getAllUser();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User  updateUser(Long id, User user );
    void deleteUser(Long id);

    String login(String email, String clave);
}

