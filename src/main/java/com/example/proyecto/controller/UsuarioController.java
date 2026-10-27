package com.example.proyecto.controller;

import com.example.proyecto.dto.UsuarioDtos.*;
import com.example.proyecto.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ===== Derechos ARCO (sobre los datos del propio usuario autenticado) =====

    // ACCESO
    @GetMapping("/me")
    public UsuarioResponse acceso(Authentication auth) {
        return usuarioService.acceso(auth.getName());
    }

    // RECTIFICACIÓN
    @PutMapping("/me")
    public UsuarioResponse rectificar(Authentication auth, @RequestBody ActualizarRequest req) {
        return usuarioService.rectificar(auth.getName(), req);
    }

    // OPOSICIÓN
    @PatchMapping("/me/oposicion")
    public UsuarioResponse oposicion(Authentication auth, @RequestBody OposicionRequest req) {
        return usuarioService.oposicion(auth.getName(), req);
    }

    // CANCELACIÓN
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(Authentication auth) {
        usuarioService.cancelar(auth.getName());
    }

    // ===== Administración (solo ROLE_ADMIN) =====
    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }
}