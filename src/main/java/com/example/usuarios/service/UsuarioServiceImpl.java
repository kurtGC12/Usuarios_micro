package com.example.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.UsuarioRepository;
import com.example.usuarios.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private UsuarioRepository userRepository;


    @Override
    public List<Usuario> getAllUser() {
        log.info("Listando usuarios...");
        List <Usuario> users = userRepository.findAll();
        log.debug("usuarios encontrados");
        return userRepository.findAll();
    }

    @Override
   public Optional<Usuario> getUserById(Long id) {
        log.info("Buscando usuario con id={}", id);
        Optional<Usuario> result = userRepository.findById(id);
        if (result.isPresent()) {
            log.debug("Usuario encontrado:", id);
        } else {
            log.warn("No se encontró usuario", id);
        }
        return result;
    }
    //Crear usuario
    @Override
    public Usuario createUser(Usuario user) {
        log.info("Creando nuevo usuario");
        Usuario saved = userRepository.save(user);
        log.debug("Usuario creado ", saved.getId());
        return userRepository.save(user);
    }
   //actualizar usuario
    @Override
    public Usuario updateUser(Long id, Usuario user) {
        log.info("Actualizando usuario", id);
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(user.getNombre());
                    existing.setEmail(user.getEmail());
                    existing.setPassword(user.getPassword());
                    existing.setRol(user.getRol());
                    Usuario updated = userRepository.save(existing);
                    log.debug("Usuario actualizado ", id);
                     return userRepository.save(user);
                })
                .orElseThrow(() -> {
                    log.warn("Intento de actualizar usuario inexistente id", id);
                    return new ResourceNotFoundException("No existe usuario con id=" + id);
                });
            }

    //Borrar usuario
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
    //buscar por email
     @Override
    public Usuario findByEmail(String email) {
        log.info("Buscando usuario por email={}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe usuario con email " + email));
    }

    //buscar por rol
    @Override
    public List<Usuario> findByRol(String rol) {
        log.info("Buscando usuarios por rol={}", rol);
        List<Usuario> usuarios = userRepository.findByRol(rol);
        log.debug("Se encontraron {} usuarios con rol {}", usuarios.size(), rol);
        return usuarios;
    }
    //registrar usuario
    @Override
    public Usuario registrar(Usuario usuario) {
        log.info("Registrando usuario con email={}", usuario.getEmail());

        userRepository.findByEmail(usuario.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("El email ya está registrado");
        });

        return userRepository.save(usuario);
    }

    //login
    @Override
    public Usuario login(String email, String clave) {
        log.info("Iniciando sesión para email={}", email);

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        // OJO: aquí asumo password en texto plano. Si usas BCrypt, cambia esta comparación.
        if (!usuario.getPassword().equals(clave)) {
            log.warn("Contraseña incorrecta para email={}", email);
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return usuario;
    }

    // recuperar por email

     @Override
    public Usuario buscarPorEmail(String email) {
        log.info("Recuperar usuario por email={}", email);
        return findByEmail(email); 
    }

    //actualizar perfil     

    @Override
    public Usuario actualizarPerfil(Long id, Usuario datos) {
        log.info("Actualizando perfil de usuario id={}", id);

        return userRepository.findById(id)
                .map(existing -> {
                    // Define qué entiendes por "perfil"
                    existing.setNombre(datos.getNombre());
                    existing.setEmail(datos.getEmail());         
                    log.debug("Perfil de usuario id={} actualizado", id);
                    return userRepository.save(existing);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe usuario con id=" + id));
    }

     /**
     * Valida que el email no esté registrado por otro usuario.
     * @param email   email a validar
     * @param idActual id del usuario actual (para actualizaciones),
     */

    private void validarEmailUnico(String email, Long idActual) {
        userRepository.findByEmail(email).ifPresent(existing -> {
            if (idActual == null || !existing.getId().equals(idActual)) {
                log.warn("⚠️ Email ya registrado: {}", email);
                throw new IllegalArgumentException("El email ya está registrado");
            }
        });
    }
    
}
