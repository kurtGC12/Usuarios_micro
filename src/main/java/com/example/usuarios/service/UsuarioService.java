package com.example.usuarios.service;


import com.example.usuarios.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    //Metodos de cada parametro
    List<Usuario> getAllUser();                     // GET /
    Optional<Usuario> getUserById(Long id);         // GET /{id}
    Usuario createUser(Usuario user);               //POST 
    Usuario  updateUser(Long id, Usuario user );   //PUT /{id}
    void deleteUser(Long id);                     //DELETE /{id}
    // Nuevas funcionalidades
    Usuario findByEmail(String email);          // GET /email/{email}
    List<Usuario> findByRol(String rol);         // GET /rol/{rol}
    Usuario registrar(Usuario usuario);         // POST /registrar
    Usuario login( String email, String password);   // POST /login
    Usuario buscarPorEmail(String email);       // GET /recuperar/{email}
    Usuario actualizarPerfil(Long id, Usuario usuario);  // PUT /{id}/perfil
  

    

}

