package com.alquilercamiones.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "camiones")
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chapa;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "capacidad_maxima", nullable = false)
    private Double capacidadMaxima;

    @OneToMany(mappedBy = "camion", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reserva> reservas;

    public Camion() {}

    public Long getId() { return id; }
    public String getChapa() { return chapa; }
    public void setChapa(String chapa) { this.chapa = chapa; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Double capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
}