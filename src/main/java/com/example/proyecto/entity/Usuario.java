package com.example.proyecto.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // Siempre guardada con BCrypt, nunca en texto plano
    @Column(nullable = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private boolean activo = true;

    // --- Campos para derechos ARCO ---
    // Consentimiento / aviso de privacidad
    @Column(name = "acepto_aviso", nullable = false)
    private boolean aceptoAviso;

    @Column(name = "fecha_aceptacion_aviso")
    private LocalDateTime fechaAceptacionAviso;

    // Oposición: no usar mis datos para fines secundarios (marketing, etc.)
    @Column(name = "oposicion_marketing", nullable = false)
    private boolean oposicionMarketing = false;

    // Cancelación
    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Usuario() {}

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public boolean isAceptoAviso() { return aceptoAviso; }
    public void setAceptoAviso(boolean aceptoAviso) { this.aceptoAviso = aceptoAviso; }
    public LocalDateTime getFechaAceptacionAviso() { return fechaAceptacionAviso; }
    public void setFechaAceptacionAviso(LocalDateTime f) { this.fechaAceptacionAviso = f; }
    public boolean isOposicionMarketing() { return oposicionMarketing; }
    public void setOposicionMarketing(boolean o) { this.oposicionMarketing = o; }
    public LocalDateTime getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(LocalDateTime f) { this.fechaCancelacion = f; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}