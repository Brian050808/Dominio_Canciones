package com.example.proyecto.repository;

import com.example.proyecto.entity.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancionRepository
        extends JpaRepository<Cancion, Long> {
}
