package com.example.usuarios.config;

import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.UsuarioRepository;   
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class UsuarioData implements CommandLineRunner {

    private final UsuarioRepository userRepository;

    public UsuarioData(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String emailAdmin = "admin@laboratorio.cl";

        userRepository.findByEmail(emailAdmin).ifPresentOrElse(
                existente -> log.info("✅ Usuario ADMIN ya existe: {}", existente.getEmail()),
                () -> {
                    log.info("⚙️ Creando usuario ADMIN por defecto");

                    Usuario admin = new Usuario();
                    admin.setNombre("Admin laboratorio");
                    admin.setEmail(emailAdmin);
                    admin.setPassword("Admin123#"); 
                    admin.setRol("ADMIN");
                
                    userRepository.save(admin);

                    log.info("✅ Usuario ADMIN creado: {} / {}", admin.getEmail(), admin.getRol());
                }
        );
    }
}
