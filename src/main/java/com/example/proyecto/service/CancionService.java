package com.example.proyecto.service;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto.repository.CancionRepository;
import com.example.proyecto.entity.Cancion;
import com.example.proyecto.entity.Album;
import com.example.proyecto.repository.AlbumRepository;
import java.util.List;
@Service
@RestController
@RequestMapping("/api/canciones")
public class CancionService {
    
    private final CancionRepository cancionRepository;
    private final AlbumRepository albumRepository;


    public CancionService(CancionRepository cancionRepository, AlbumRepository albumRepository) {
        this.cancionRepository = cancionRepository;
        this.albumRepository = albumRepository;
    }

    public List<Cancion> ListarCanciones() {
        return cancionRepository.findAll();
    }

    public Cancion buscarCancion(Long id) {
        return cancionRepository.findById(id).orElse(null);
    }

    public Cancion guardarCancion(Cancion cancion) {
        Integer albumId = cancion.getAlbum().getId();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album no encontrado con id: " + albumId));
        cancion.setAlbum(album);
        return cancionRepository.save(cancion);
    }

    public void eliminarCancion(Long id) {
        cancionRepository.deleteById(id);
    }
    public Cancion actualizarCancion(Long id, Cancion datosNuevos) {
        Cancion cancionExistente = cancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la canción con ID: " + id));
        cancionExistente.setTitulo(datosNuevos.getTitulo());
        cancionExistente.setDuracionSegundos(datosNuevos.getDuracionSegundos());

        Integer albumId = datosNuevos.getAlbum().getId();

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("No existe el álbum con ID: " + albumId));

        cancionExistente.setAlbum(album);

        return cancionRepository.save(cancionExistente);
    }
    
}
