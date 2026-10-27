package com.example.proyecto.controller;

import com.example.proyecto.entity.Artista;
import com.example.proyecto.service.ArtistaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public List<Artista> listarArtistas() {
        return artistaService.ListarArtistas();
    }

    @GetMapping("/{id}")
    public Artista buscarArtista(@PathVariable Integer id) {
        return artistaService.buscarArtista(id);
    }

    @PostMapping
    public Artista guardarArtista(@RequestBody Artista artista) {
        return artistaService.guardarArtista(artista);
    }

    @DeleteMapping("/{id}")
    public void eliminarArtista(@PathVariable Integer id) {
        artistaService.eliminarArtista(id);
    }
    @PutMapping ("/{id}")
    public Artista actualizarArtista(@PathVariable Integer id, @RequestBody Artista artista) {
        return artistaService.actualizarArtista(id, artista);
}
}