package com.example.proyecto.dto;

import java.time.LocalDateTime;

// DTOs: así nunca se expone el password en las respuestas JSON
public class UsuarioDtos {

    public record RegistroRequest(String nombre, String email, String password, Boolean aceptaAviso) {}

    public record ActualizarRequest(String nombre, String email, String password) {}

    public record OposicionRequest(Boolean oposicionMarketing) {}

    public record UsuarioResponse(
            Integer id,
            String nombre,
            String email,
            String rol,
            boolean activo,
            boolean aceptoAviso,
            LocalDateTime fechaAceptacionAviso,
            boolean oposicionMarketing,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {}
}