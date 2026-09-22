package com.aydsii.TP2_LAMOTH.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.TP2_LAMOTH.dto.ClienteDTO;
import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;
import com.aydsii.TP2_LAMOTH.model.Cliente;
import com.aydsii.TP2_LAMOTH.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Clientes", description = "Alta de clientes en base de datos MySQL")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Alta simple de un cliente (sin validaciones)")
    @PostMapping
    public ResponseEntity<RespuestaApi<Cliente>> crear(@RequestBody ClienteDTO dto) {
        Cliente creado = clienteService.crear(dto);
        RespuestaApi<Cliente> respuesta = new RespuestaApi<>(201, "Cliente creado con éxito", creado);
        return ResponseEntity.status(201).body(respuesta);
    }

    @Operation(summary = "Alta de un cliente con validaciones (Bean Validation + email único)")
    @PostMapping("/validado")
    public ResponseEntity<RespuestaApi<Cliente>> crearValidado(@RequestBody @Valid ClienteDTO dto) {
        Cliente creado = clienteService.crearValidado(dto);
        RespuestaApi<Cliente> respuesta = new RespuestaApi<>(201, "Cliente creado con éxito", creado);
        return ResponseEntity.status(201).body(respuesta);
    }
}
