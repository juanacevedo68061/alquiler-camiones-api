package com.alquilercamiones.service;

import com.alquilercamiones.entity.Camion;
import com.alquilercamiones.entity.Cliente;
import com.alquilercamiones.entity.Reserva;
import com.alquilercamiones.repository.CamionRepository;
import com.alquilercamiones.repository.ClienteRepository;
import com.alquilercamiones.repository.ReservaRepository;
import com.alquilercamiones.dto.ReservaDTO;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ReservaService {

    @Inject
    private ReservaRepository repository;

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private CamionRepository camionRepository;

    public List<Reserva> findAll() {
        return repository.findAll();
    }

    public Reserva findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    public List<Reserva> findEnCurso() {
        return repository.findEnCurso(LocalDate.now());
    }

    public List<Reserva> findFinalizadas() {
        return repository.findFinalizadas(LocalDate.now());
    }

    public List<Reserva> findProximas() {
        return repository.findProximas(LocalDate.now());
    }

    public Reserva save(ReservaDTO dto) {
        if (dto.fechaInicio.isAfter(dto.fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser mayor a la fecha fin");
        }

        if (dto.fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser menor a hoy");
        }

        Cliente cliente = clienteRepository.findById(dto.clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + dto.clienteId));

        Camion camion = camionRepository.findById(dto.camionId)
            .orElseThrow(() -> new RuntimeException("Camion no encontrado: " + dto.camionId));

        if (dto.volumenCarga > camion.getCapacidadMaxima()) {
            throw new IllegalArgumentException("El volumen de carga supera la capacidad maxima del camion: " + camion.getCapacidadMaxima());
        }

        if (repository.existeSolapamiento(dto.camionId, dto.fechaInicio, dto.fechaFin)) {
            throw new IllegalArgumentException("El camion ya tiene una reserva en esas fechas");
        }

        Reserva reserva = new Reserva();
        reserva.setOrigen(dto.origen);
        reserva.setDestino(dto.destino);
        reserva.setFechaInicio(dto.fechaInicio);
        reserva.setFechaFin(dto.fechaFin);
        reserva.setVolumenCarga(dto.volumenCarga);
        reserva.setCliente(cliente);
        reserva.setCamion(camion);

        return repository.save(reserva);
    }

    public void delete(Long id) {
        Reserva reserva = findById(id);
        if (!reserva.getFechaInicio().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede cancelar una reserva que ya inicio");
        }
        repository.delete(id);
    }
}