package com.aydsii.TP2_LAMOTH.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.TP2_LAMOTH.dto.ConversionDTO;
import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;
import com.aydsii.TP2_LAMOTH.exception.ParametrosInvalidosException;
import com.aydsii.TP2_LAMOTH.service.DivisaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Divisas", description = "Conversor de divisas usando la API externa Frankfurter")
@RestController
@RequestMapping("/api/divisas")
public class DivisaController {
    @Autowired
    private DivisaService divisaService;

    @Operation(summary = "Convertir un monto entre dos divisas")
    @GetMapping("/convertir")
    public ResponseEntity<RespuestaApi<ConversionDTO>> convertir(
            @RequestParam double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        if (monto <= 0) {
            throw new ParametrosInvalidosException("El monto debe ser mayor que 0");
        }
        if (origen == null || !origen.matches("[a-zA-Z]{3}")) {
            throw new ParametrosInvalidosException("El código de moneda de origen debe tener 3 letras");
        }
        if (destino == null || !destino.matches("[a-zA-Z]{3}")) {
            throw new ParametrosInvalidosException("El código de moneda de destino debe tener 3 letras");
        }

        ConversionDTO conversion = divisaService.convertir(monto, origen.toUpperCase(), destino.toUpperCase());
        RespuestaApi<ConversionDTO> respuesta = new RespuestaApi<>(200, "Conversión realizada con éxito", conversion);
        return ResponseEntity.ok(respuesta);
    }
}
