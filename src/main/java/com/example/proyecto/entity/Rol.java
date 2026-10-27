package com.example.proyecto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // "ADMIN" o "USUARIO"
    @Column(nullable = false, unique = true, length = 30)
    private String nombre;

    public Rol() {}
    public Rol(String nombre) { this.nombre = nombre; }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}