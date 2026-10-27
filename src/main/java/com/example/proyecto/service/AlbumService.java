package com.example.proyecto.service;

import org.springframework.stereotype.Service;
import com.example.proyecto.repository.AlbumRepository;
import com.example.proyecto.repository.ArtistaRepository;
import com.example.proyecto.entity.Album;
import com.example.proyecto.entity.Artista;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumService(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    public List<Album> ListarAlbumes() {
        return albumRepository.findAll();
    }

    public Album buscarAlbum(Integer id) {
        return albumRepository.findById(id).orElse(null);
    }

    public Album guardarAlbum(Album album) {
        Integer artisaId = album.getArtista().getId();
        Artista artista = artistaRepository.findById(artisaId)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con id: " + artisaId));
        album.setArtista(artista);
        return albumRepository.save(album);
    }

    public void eliminarAlbum(Integer id) {
        albumRepository.deleteById(id);
    }

    public Album actualizarAlbum(Integer id, Album datosNuevos) {
        Album albumExistente = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el álbum con ID: " + id));
        albumExistente.setTitulo(datosNuevos.getTitulo());
        albumExistente.setAnioLanzamiento(datosNuevos.getAnioLanzamiento());

        Integer artistaId = datosNuevos.getArtista().getId();

        Artista artista = artistaRepository.findById(artistaId)
                .orElseThrow(() -> new RuntimeException("No existe el artista con ID: " + artistaId));

        albumExistente.setArtista(artista);

        return albumRepository.save(albumExistente);

    }

}
