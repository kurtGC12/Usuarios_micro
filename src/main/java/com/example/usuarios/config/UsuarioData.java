package com.example.usuarios.config;

import com.example.usuarios.model.User;
import com.example.usuarios.repository.UserRepository;   
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class UsuarioData implements CommandLineRunner {

    private final UserRepository userRepository;

    public UsuarioData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String emailAdmin = "admin@laboratorio.cl";

        userRepository.findByEmail(emailAdmin).ifPresentOrElse(
                existente -> log.info("✅ Usuario ADMIN ya existe: {}", existente.getEmail()),
                () -> {
                    log.info("⚙️ Creando usuario ADMIN por defecto");

                    User admin = new User();
                    admin.setNombre("Admin laboratorio");
                    admin.setEmail(emailAdmin);
                    admin.setPassword("admin123"); 
                    admin.setRol("ADMIN");
                
                    userRepository.save(admin);

                    log.info("✅ Usuario ADMIN creado: {} / {}", admin.getEmail(), admin.getRol());
                }
        );
    }
}
