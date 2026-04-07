package com.alquilercamiones.repository;

import com.alquilercamiones.entity.Camion;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CamionRepository {

    @PersistenceContext(unitName = "appPU")
    private EntityManager em;

    public List<Camion> findAll() {
        return em.createQuery("SELECT c FROM Camion c", Camion.class).getResultList();
    }

    public Optional<Camion> findById(Long id) {
        return Optional.ofNullable(em.find(Camion.class, id));
    }

    public Camion save(Camion camion) {
        em.persist(camion);
        return camion;
    }

    public Camion update(Camion camion) {
        return em.merge(camion);
    }

    public void delete(Long id) {
        Camion c = em.find(Camion.class, id);
        if (c != null) em.remove(c);
    }

    public List<Camion> findDisponibles(LocalDate fechaInicio, LocalDate fechaFin, Double volumen) {
        StringBuilder jpql = new StringBuilder(
            "SELECT c FROM Camion c WHERE 1=1"
        );

        if (fechaInicio != null && fechaFin != null) {
            jpql.append(" AND c.id NOT IN (" +
                "SELECT r.camion.id FROM Reserva r " +
                "WHERE r.fechaInicio <= :fechaFin " +
                "AND r.fechaFin >= :fechaInicio)");
        }

        if (volumen != null) {
            jpql.append(" AND c.capacidadMaxima >= :volumen");
        }

        TypedQuery<Camion> query = em.createQuery(jpql.toString(), Camion.class);

        if (fechaInicio != null && fechaFin != null) {
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
        }

        if (volumen != null) {
            query.setParameter("volumen", volumen);
        }

        return query.getResultList();
    }
}