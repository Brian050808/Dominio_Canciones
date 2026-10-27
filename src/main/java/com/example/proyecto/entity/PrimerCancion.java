package com.example.proyecto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class PrimerCancion {
    private int id;
    private String artista;
    private String genero;
    private String cancion;

    public PrimerCancion(int id, String artista, String genero, String cancion) {
        this.id = id;
        this.artista = artista;
        this.genero = genero;
        this.cancion = cancion;
    }
    public int id() {
        return id;
    }
}