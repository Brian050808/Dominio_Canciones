
package com.example.proyecto.controller;
import com.example.proyecto.entity.PrimerCancion;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrimerController {

    private final List<PrimerCancion> canciones = List.of(
        new PrimerCancion(1, "Junior H", "Corridos Tumbados", "Piensalo"),
        new PrimerCancion(2, "Trueno", "Rap", "Real Gangsta Love"),
        new PrimerCancion(3, "Fuerza Regida", "Corridos Tumbados", "Sigan Hablando")
    );

    @GetMapping("/canciones")
    public List<PrimerCancion> obtenerCanciones() {
        return canciones;
    }

    @GetMapping("/canciones/{id}")
    public PrimerCancion obtenerCancionPorId(@PathVariable int id) {
        return canciones.stream()
            .filter(cancion -> cancion.id() == id)
                .findFirst()
                .orElse(null);
    }
}
