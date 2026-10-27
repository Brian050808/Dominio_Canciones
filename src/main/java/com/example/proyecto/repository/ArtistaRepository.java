package com.example.proyecto.repository;

import com.example.proyecto.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository
        extends JpaRepository<Artista, Integer> {
}