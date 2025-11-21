package com.example.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuarios.model.User;
import com.example.usuarios.repository.UserRepository;
import com.example.usuarios.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAllUser() {
        log.info("Listando usuarios...");
        List <User> users = userRepository.findAll();
        log.debug("usuarios encontrados");
        return userRepository.findAll();
    }

    @Override
   public Optional<User> getUserById(Long id) {
        log.info("Buscando usuario con id={}", id);
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            log.debug("Usuario encontrado:", id);
        } else {
            log.warn("No se encontró usuario", id);
        }
        return result;
    }

    @Override
    public User createUser(User user) {
        log.info("Creando nuevo usuario");
        User saved = userRepository.save(user);
        log.debug("Usuario creado ", saved.getId());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        log.info("Actualizando usuario", id);
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(user.getNombre());
                    existing.setEmail(user.getEmail());
                    existing.setPassword(user.getPassword());
                    existing.setRol(user.getRol());
                    User updated = userRepository.save(existing);
                    log.debug("Usuario actualizado ", id);
                     return userRepository.save(user);
                })
                .orElseThrow(() -> {
                    log.warn("Intento de actualizar usuario inexistente id", id);
                    return new ResourceNotFoundException("No existe usuario con id=" + id);
                });
            }

    @Override
    public void deleteUser(Long id) {
        log.info("Eliminando usuario id={}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.debug("Usuario eliminado id", id);
        } else {
            log.warn("Intento de eliminar usuario inexistente ", id);
            throw new ResourceNotFoundException("No existe usuario " + id);
        }
    }

    @Override
    public String login(String email, String clave) {
    log.info("Iniciando sesión para el usuario con email: {}", email);
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}
