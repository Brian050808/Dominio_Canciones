package com.example.proyecto.entity;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column (nullable = false, length = 80)
    private String pais;

    @Column (name = "genero_musical", length = 80)
    private String generoMusical;

    @CreationTimestamp 
    @Column (name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public Artista() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

}
