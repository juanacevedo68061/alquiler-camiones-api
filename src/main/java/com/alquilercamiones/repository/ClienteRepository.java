package com.alquilercamiones.repository;

import com.alquilercamiones.entity.Cliente;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepository {

    @PersistenceContext(unitName = "appPU")
    private EntityManager em;

    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(em.find(Cliente.class, id));
    }

    public Cliente save(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }

    public void delete(Long id) {
        Cliente c = em.find(Cliente.class, id);
        if (c != null) em.remove(c);
    }
}