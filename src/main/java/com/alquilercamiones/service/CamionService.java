package com.alquilercamiones.service;

import com.alquilercamiones.entity.Camion;
import com.alquilercamiones.repository.CamionRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class CamionService {

    @Inject
    private CamionRepository repository;

    public List<Camion> findAll() {
        return repository.findAll();
    }

    public Camion findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Camion no encontrado: " + id));
    }

    public Camion save(Camion camion) {
        return repository.save(camion);
    }

    public Camion update(Long id, Camion datos) {
        if (datos.getDescripcion() == null && datos.getCapacidadMaxima() == null) {
            throw new IllegalArgumentException("Debe enviar al menos un campo a actualizar");
        }
        Camion existente = findById(id);
        if (datos.getDescripcion() != null) {
            existente.setDescripcion(datos.getDescripcion());
        }
        if (datos.getCapacidadMaxima() != null) {
            existente.setCapacidadMaxima(datos.getCapacidadMaxima());
        }
        return repository.update(existente);
    }

    public void delete(Long id) {
        findById(id);
        repository.delete(id);
    }

    public List<Camion> findDisponibles(LocalDate fechaInicio, LocalDate fechaFin, Double volumen) {
        return repository.findDisponibles(fechaInicio, fechaFin, volumen);
    }
}