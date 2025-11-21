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
    

 // GET /api/usuarios
    @GetMapping
    public List<User> getAll() {
        log.info("Listando usuarios...");
        log.debug("Usuarios encontrados");
        return userService.getAllUser();
        
    }

    //METODO GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
         return userService.getUserById(id)
                .map(user -> {
                    log.debug("Usuario encontrado id", id);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    log.warn("No se encontró usuario id", id);
                    return ResponseEntity.notFound().build();
                });
    }

    //METODO POST /api/users
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("Creando nuevo usuario...");
        User nuevo = userService.createUser(user);
        log.debug("Usuario creado con id: {}", nuevo.getId());
        log.error("Error al crear usuario");
         return ResponseEntity.status(201).body(nuevo); // Devuelve 201 nuevo
    }

    // METODO PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
        log.info("Actualizando usuario con id:", id);
        User updated = userService.updateUser(id, user);
        if (updated == null) {
            log.info("Usuario con id {} no encontrado para actualizar", id);
            log.error("Usuario con id {} no encontrado para actualizar", id);
            return ResponseEntity.notFound().build();// Si no → 404
        }
        log.debug("Usuario con id {} actualizado", id);
        return ResponseEntity.ok(updated);// Si lo encuentra → 200 OK
    }

    // METODO DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // si no existe, tu service no lanza excepción; devolvemos 204 siempre
        log.info("Eliminando usuario con id:", id);
        log.error("Error al eliminar usuario con id: {}", id);
        userService.deleteUser(id);
        log.debug("Usuario con id {} eliminado", id);
        return ResponseEntity.noContent().build();// Eliminado → 204
    }
}    

