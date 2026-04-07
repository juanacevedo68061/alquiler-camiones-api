package com.alquilercamiones.service;

import com.alquilercamiones.entity.Cliente;
import com.alquilercamiones.repository.ClienteRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ClienteService {

    @Inject
    private ClienteRepository repository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public void delete(Long id) {
        findById(id);
        repository.delete(id);
    }
}