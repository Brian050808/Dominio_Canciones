package com.example.proyecto.controller;
import com.example.proyecto.entity.Cancion;
import com.example.proyecto.service.CancionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cancion")
public class CancionController {

    private final CancionService cancionService;

    public CancionController(CancionService cancionService) {
        this.cancionService = cancionService;
    }

    @GetMapping
    public List<Cancion> listarCanciones() {
        return cancionService.ListarCanciones();
    }

    @GetMapping("/{id}")
    public Cancion buscarCancion(@PathVariable Long id) {
        return cancionService.buscarCancion(id);
    }

    @PostMapping
    public Cancion guardarCancion(@RequestBody Cancion cancion) {
        return cancionService.guardarCancion(cancion);
    }

    @PutMapping("/{id}")
    public Cancion actualizarCancion(@PathVariable Long id, @RequestBody Cancion datosNuevos) {
        return cancionService.actualizarCancion(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    public void eliminarCancion(@PathVariable Long id) {
        cancionService.eliminarCancion(id);
    }

}
