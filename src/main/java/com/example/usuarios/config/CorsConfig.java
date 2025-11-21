
package com.example.usuarios.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

          // Este método sobreescribe la configuración por defecto de Spring
            @Override
            public void addCorsMappings(CorsRegistry registry) {

              
                registry.addMapping("/api/**")         
                        // Permitir todas las fuentes             
                        .allowedOrigins("*")
                        // Permitir todos los métodos HTTP
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")          
                        // Permitir todos los encabezados            
                        .allowedHeaders("*")                       
                        .allowCredentials(false);
            }
        };
    }
}
