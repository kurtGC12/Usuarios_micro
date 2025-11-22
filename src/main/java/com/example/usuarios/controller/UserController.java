package com.example.usuarios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.model.LoginRequest;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.service.UsuarioService;

import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UserController {

     @Autowired
    private UsuarioService userService;

     public UserController(UsuarioService userService) {
        this.userService = userService;
    }
    

 // GET /api/usuarios
    @GetMapping
    public List<Usuario> getAll() {
        log.info("Listando usuarios...");
        log.debug("Usuarios encontrados");
        return userService.getAllUser();
        
    }

    //METODO GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
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

    //METODO POST /api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario user) {
        log.info("Creando nuevo usuario...");
        Usuario nuevo = userService.createUser(user);
        log.debug("Usuario creado con id: {}", nuevo.getId());
        log.error("Error al crear usuario");
         return ResponseEntity.status(201).body(nuevo); // Devuelve 201 nuevo
    }

    // METODO PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @Valid @RequestBody Usuario user) {
        log.info("Actualizando usuario con id:", id);
        Usuario updated = userService.updateUser(id, user);
        if (updated == null) {
            log.info("Usuario con id {} no encontrado para actualizar", id);
            log.error("Usuario con id {} no encontrado para actualizar", id);
            return ResponseEntity.notFound().build();// Si no → 404
        }
        log.debug("Usuario con id {} actualizado", id);
        return ResponseEntity.ok(updated);// Si lo encuentra → 200 OK
    }

    // METODO DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // si no existe, tu service no lanza excepción; devolvemos 204 siempre
        log.info("Eliminando usuario con id:", id);
        log.error("Error al eliminar usuario con id: {}", id);
        userService.deleteUser(id);
        log.debug("Usuario con id {} eliminado", id);
        return ResponseEntity.noContent().build();// Eliminado → 204
    }
    //nuevas funcionalidades

     //METODO GET /api/usuarios/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        log.info("📧 [GET] Buscar por email: {}", email);
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    //METODO GET /api/usuarios/rol/{rol}
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> findByRol(@PathVariable String rol) {
        log.info("🎯 [GET] Usuarios por rol: {}", rol);
        List<Usuario> usuarios = userService.findByRol(rol);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    //METODO POST /api/usuarios/registro
     @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        log.info("📝 [POST] Registro de usuario desde FrontEnd: {}", usuario.getEmail());
        Usuario creado = userService.registrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    //METODO POST /api/usuarios/login
     @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        log.info("🔐 [POST] Login para email: {}", request.getEmail());
        Usuario usuario = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(usuario);
    }
    //METODO GET /api/usuarios/recuperar/{email}   
    @GetMapping("/recuperar/{email}")
    public ResponseEntity<Usuario> recuperarPorEmail(@PathVariable String email) {
        log.info("📧 [GET] Recuperar usuario por email: {}", email);
        return ResponseEntity.ok(userService.buscarPorEmail(email));
    }
    //METODO PUT /api/usuarios/{id}/perfil
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(@PathVariable Long id,
                                                    @Valid @RequestBody Usuario usuario) {
        log.info("👤 [PUT] Actualizar perfil de usuario ID: {}", id);
        return ResponseEntity.ok(userService.actualizarPerfil(id, usuario));
    }




}    

