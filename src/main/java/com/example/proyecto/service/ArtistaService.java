package com.example.proyecto.service;

import org.springframework.stereotype.Service;
import com.example.proyecto.repository.ArtistaRepository;
import com.example.proyecto.entity.Artista;
import java.util.List;
@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public List<Artista> ListarArtistas() {
        return artistaRepository.findAll();
    }

    public Artista buscarArtista(Integer id) {
        return artistaRepository.findById(id).orElse(null);
    }

    public Artista guardarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    public void eliminarArtista(Integer id) {
        artistaRepository.deleteById(id);
    }
    public Artista actualizarArtista(Integer id, Artista artista) {
        Artista artistaExistente = artistaRepository.findById(id).orElse(null);
        if (artistaExistente != null) {
            artistaExistente.setNombre(artista.getNombre());
            artistaExistente.setPais(artista.getPais());
            artistaExistente.setGeneroMusical(artista.getGeneroMusical());
            return artistaRepository.save(artistaExistente);
        }
        return null;
    }

   
    
}
