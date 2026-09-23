package com.aydsii.TP2_LAMOTH.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aydsii.TP2_LAMOTH.dto.ConversionDTO;
import com.aydsii.TP2_LAMOTH.dto.HistorialCotizacionDTO;
import com.aydsii.TP2_LAMOTH.model.HistorialConversion;
import com.aydsii.TP2_LAMOTH.repository.HistorialConversionRepository;

@Service
public class HistorialService {

    @Autowired
    private DivisaService divisaService;

    @Autowired
    private HistorialConversionRepository historialRepository;

    public ConversionDTO consultarYGuardar(double monto, String origen, String destino) {
        ConversionDTO conversion = divisaService.convertir(monto, origen, destino);

        HistorialConversion historial = new HistorialConversion();
        historial.setMonedaOrigen(origen);
        historial.setMonedaDestino(destino);
        historial.setMonto(BigDecimal.valueOf(monto));
        historial.setMontoConvertido(BigDecimal.valueOf(conversion.getMontoConvertido()));
        historial.setTasa(BigDecimal.valueOf(conversion.getTasaCambio()));
        historial.setFechaConsulta(LocalDateTime.now());

        historialRepository.save(historial);

        return conversion;
    }

    public List<HistorialCotizacionDTO> obtenerHistorial(String origen, String destino) {
        List<HistorialConversion> registros = historialRepository
                .findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(origen, destino);

        return registros.stream()
                .map(h -> new HistorialCotizacionDTO(h.getFechaConsulta(), h.getTasa().doubleValue()))
                .collect(Collectors.toList());
    }
}