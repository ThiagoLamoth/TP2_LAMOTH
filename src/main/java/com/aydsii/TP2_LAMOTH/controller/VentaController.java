package com.aydsii.TP2_LAMOTH.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.TP2_LAMOTH.dto.EstadisticasVentaDTO;
import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;
import com.aydsii.TP2_LAMOTH.dto.ResultadoDescuentoDTO;
import com.aydsii.TP2_LAMOTH.dto.VentaDTO;
import com.aydsii.TP2_LAMOTH.exception.ListaVaciaException;
import com.aydsii.TP2_LAMOTH.exception.PorcentajeInvalidoException;
import com.aydsii.TP2_LAMOTH.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Ventas", description = "Análisis de lotes de ventas")
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Calcular estadísticas de un lote de ventas")
    @PostMapping("/estadisticas")
    public ResponseEntity<RespuestaApi<EstadisticasVentaDTO>> obtenerEstadisticas(
            @RequestBody List<@Valid VentaDTO> ventas) {

        if (ventas == null || ventas.isEmpty()) {
            throw new ListaVaciaException("La lista de ventas no puede estar vacía");
        }

        EstadisticasVentaDTO estadisticas = ventaService.calcularEstadisticas(ventas);
        RespuestaApi<EstadisticasVentaDTO> respuesta =
                new RespuestaApi<>(200, "Estadísticas calculadas con éxito", estadisticas);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Aplicar un descuento a un lote de ventas")
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<RespuestaApi<ResultadoDescuentoDTO>> aplicarDescuento(
        @RequestBody List<@Valid VentaDTO> ventas,
        @RequestParam double porcentaje) {

        if (ventas == null || ventas.isEmpty()) {
            throw new ListaVaciaException("La lista de ventas no puede estar vacía");
        }
        if (porcentaje < 0 || porcentaje > 100) {
            throw new PorcentajeInvalidoException("El porcentaje debe estar entre 0 y 100");
        }

        ResultadoDescuentoDTO resultado = ventaService.aplicarDescuento(ventas, porcentaje);
        RespuestaApi<ResultadoDescuentoDTO> respuesta =
            new RespuestaApi<>(200, "Descuento aplicado con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }
}