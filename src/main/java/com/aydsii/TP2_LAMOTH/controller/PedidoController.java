package com.aydsii.TP2_LAMOTH.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.TP2_LAMOTH.dto.PedidoConsultaDTO;
import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;
import com.aydsii.TP2_LAMOTH.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Consulta del historial de pedidos con filtros combinables")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Buscar pedidos con filtros opcionales combinables")
    @GetMapping("/buscar")
    public ResponseEntity<RespuestaApi<List<PedidoConsultaDTO>>> buscar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String estado) {

        List<PedidoConsultaDTO> resultado = pedidoService.buscar(clienteId, categoria, fechaDesde, fechaHasta, estado);
        RespuestaApi<List<PedidoConsultaDTO>> respuesta =
                new RespuestaApi<>(200, "Consulta realizada correctamente", resultado);
        return ResponseEntity.ok(respuesta);
    }
}
