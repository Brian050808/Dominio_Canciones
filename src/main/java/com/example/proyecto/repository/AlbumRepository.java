package com.example.proyecto.repository;

import com.example.proyecto.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository 
extends JpaRepository<Album, Integer> {
}
