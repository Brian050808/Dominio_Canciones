package com.example.proyecto.controller;
import com.example.proyecto.entity.Album;
import com.example.proyecto.service.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/albumes")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> listarAlbums() {
        return albumService.ListarAlbumes();
    }

    @GetMapping("/{id}")
    public Album buscarAlbum(@PathVariable Integer id) {
        return albumService.buscarAlbum(id);
    }

    @PostMapping
    public Album guardarAlbum(@RequestBody Album album) {
        return albumService.guardarAlbum(album);
    }

    @DeleteMapping("/{id}")
    public void eliminarAlbum(@PathVariable Integer id) {
        albumService.eliminarAlbum(id);
    }
    @PutMapping ("/{id}")
    public Album actualizarAlbum(@PathVariable Integer id, @RequestBody Album album) {
        return albumService.actualizarAlbum(id, album);
    }
}
