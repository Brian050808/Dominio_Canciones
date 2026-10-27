package com.example.proyecto.service;

import com.example.proyecto.dto.UsuarioDtos.*;
import com.example.proyecto.entity.Rol;
import com.example.proyecto.entity.Usuario;
import com.example.proyecto.repository.RolRepository;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- Registro (consentimiento) ----------
    public UsuarioResponse registrar(RegistroRequest req) {
        if (req.nombre() == null || req.email() == null || req.password() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre, email y password son obligatorios");
        if (!Boolean.TRUE.equals(req.aceptaAviso()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes aceptar el aviso de privacidad");
        if (usuarioRepository.existsByEmail(req.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");

        Rol rol = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol USUARIO no existe"));

        Usuario u = new Usuario();
        u.setNombre(req.nombre());
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setRol(rol);
        u.setAceptoAviso(true);
        u.setFechaAceptacionAviso(LocalDateTime.now());
        return toResponse(usuarioRepository.save(u));
    }

    // ---------- A: ACCESO ----------
    public UsuarioResponse acceso(String email) {
        return toResponse(buscarActivoPorEmail(email));
    }

    // (solo ADMIN)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findByActivoTrue().stream().map(this::toResponse).toList();
    }

    public UsuarioResponse buscarPorId(Integer id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return toResponse(u);
    }

    // ---------- R: RECTIFICACIÓN ----------
    public UsuarioResponse rectificar(String emailActual, ActualizarRequest req) {
        Usuario u = buscarActivoPorEmail(emailActual);

        if (req.nombre() != null && !req.nombre().isBlank())
            u.setNombre(req.nombre());

        if (req.email() != null && !req.email().isBlank() && !req.email().equals(u.getEmail())) {
            if (usuarioRepository.existsByEmail(req.email()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está en uso");
            u.setEmail(req.email());
        }

        if (req.password() != null && !req.password().isBlank())
            u.setPassword(passwordEncoder.encode(req.password()));

        return toResponse(usuarioRepository.save(u));
    }

    // ---------- C: CANCELACIÓN ----------
    // Borrado lógico + anonimización de datos personales
    public void cancelar(String email) {
        Usuario u = buscarActivoPorEmail(email);
        u.setActivo(false);
        u.setNombre("USUARIO CANCELADO");
        u.setEmail("cancelado_" + u.getId() + "@anonimo.local");
        u.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // ya nadie puede entrar
        u.setOposicionMarketing(true);
        u.setFechaCancelacion(LocalDateTime.now());
        usuarioRepository.save(u);
    }

    // ---------- O: OPOSICIÓN ----------
    public UsuarioResponse oposicion(String email, OposicionRequest req) {
        if (req.oposicionMarketing() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "oposicionMarketing es obligatorio");
        Usuario u = buscarActivoPorEmail(email);
        u.setOposicionMarketing(req.oposicionMarketing());
        return toResponse(usuarioRepository.save(u));
    }

    // ---------- helpers ----------
    private Usuario buscarActivoPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .filter(Usuario::isActivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail(), u.getRol().getNombre(),
                u.isActivo(), u.isAceptoAviso(), u.getFechaAceptacionAviso(),
                u.isOposicionMarketing(), u.getFechaCreacion(), u.getFechaActualizacion());
    }
}