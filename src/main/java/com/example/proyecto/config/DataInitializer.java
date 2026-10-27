package com.example.proyecto.config;

import com.example.proyecto.entity.Rol;
import com.example.proyecto.entity.Usuario;
import com.example.proyecto.repository.RolRepository;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository roles, UsuarioRepository usuarios, PasswordEncoder encoder,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${app.admin.password:}") String adminPassword) {
        return args -> {
            Rol admin = roles.findByNombre("ADMIN").orElseGet(() -> roles.save(new Rol("ADMIN")));
            roles.findByNombre("USUARIO").orElseGet(() -> roles.save(new Rol("USUARIO")));

            if (!adminEmail.isBlank() && !adminPassword.isBlank()
                    && !usuarios.existsByEmail(adminEmail)) {
                Usuario u = new Usuario();
                u.setNombre("Administrador");
                u.setEmail(adminEmail);
                u.setPassword(encoder.encode(adminPassword));
                u.setRol(admin);
                u.setAceptoAviso(true);
                u.setFechaAceptacionAviso(LocalDateTime.now());
                usuarios.save(u);
            }
        };
    }
}