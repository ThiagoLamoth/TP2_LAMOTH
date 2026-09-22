package com.aydsii.TP2_LAMOTH.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aydsii.TP2_LAMOTH.dto.ClienteDTO;
import com.aydsii.TP2_LAMOTH.exception.EmailDuplicadoException;
import com.aydsii.TP2_LAMOTH.model.Cliente;
import com.aydsii.TP2_LAMOTH.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crear(ClienteDTO dto) {
        Cliente cliente = new Cliente(dto.getNombre(), dto.getApellido(), dto.getEmail(), dto.getTelefono());
        return clienteRepository.save(cliente);
    }

    public Cliente crearValidado(ClienteDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicadoException("El email ya está registrado");
        }
        return crear(dto);
    }
}
