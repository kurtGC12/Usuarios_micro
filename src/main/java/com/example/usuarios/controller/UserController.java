package com.example.usuarios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.model.User;
import com.example.usuarios.service.UserService;

import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UserController {

     @Autowired
    private UserService userService;

     public UserController(UserService userService) {
        this.userService = userService;
    }
    

 // GET /api/users
    @GetMapping
    public List<User> getAll() {
        log.info("Listando usuarios...");
        return userService.getAllUser();
    }

    //METODO GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // Si lo encuentra → 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no → 404
    }

    //METODO POST /api/users
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User nuevo = userService.createUser(user);
         return ResponseEntity.status(201).body(nuevo); // Devuelve 201 nuevo
    }

    // METODO PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        if (updated == null) {
            return ResponseEntity.notFound().build();// Si no → 404
        }
        return ResponseEntity.ok(updated);// Si lo encuentra → 200 OK
    }

    // METODO DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // si no existe, tu service no lanza excepción; devolvemos 204 siempre
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();// Eliminado → 204
    }
}    

