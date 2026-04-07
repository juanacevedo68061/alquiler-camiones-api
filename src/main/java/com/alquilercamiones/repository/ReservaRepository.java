package com.alquilercamiones.repository;

import com.alquilercamiones.entity.Reserva;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ReservaRepository {

    @PersistenceContext(unitName = "appPU")
    private EntityManager em;

    public List<Reserva> findAll() {
        return em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
    }

    public Optional<Reserva> findById(Long id) {
        return Optional.ofNullable(em.find(Reserva.class, id));
    }

    public List<Reserva> findEnCurso(LocalDate hoy) {
        return em.createQuery(
            "SELECT r FROM Reserva r WHERE r.fechaInicio <= :hoy AND r.fechaFin >= :hoy",
            Reserva.class)
            .setParameter("hoy", hoy)
            .getResultList();
    }

    public List<Reserva> findFinalizadas(LocalDate hoy) {
        return em.createQuery(
            "SELECT r FROM Reserva r WHERE r.fechaFin < :hoy",
            Reserva.class)
            .setParameter("hoy", hoy)
            .getResultList();
    }

    public List<Reserva> findProximas(LocalDate hoy) {
        return em.createQuery(
            "SELECT r FROM Reserva r WHERE r.fechaInicio > :hoy",
            Reserva.class)
            .setParameter("hoy", hoy)
            .getResultList();
    }

    public boolean existeSolapamiento(Long camionId, LocalDate fechaInicio, LocalDate fechaFin) {
        Long count = em.createQuery(
            "SELECT COUNT(r) FROM Reserva r " +
            "WHERE r.camion.id = :camionId " +
            "AND r.fechaInicio <= :fechaFin " +
            "AND r.fechaFin >= :fechaInicio", Long.class)
            .setParameter("camionId", camionId)
            .setParameter("fechaInicio", fechaInicio)
            .setParameter("fechaFin", fechaFin)
            .getSingleResult();
        return count > 0;
    }

    public Reserva save(Reserva reserva) {
        em.persist(reserva);
        return reserva;
    }

    public void delete(Long id) {
        Reserva r = em.find(Reserva.class, id);
        if (r != null) em.remove(r);
    }
}