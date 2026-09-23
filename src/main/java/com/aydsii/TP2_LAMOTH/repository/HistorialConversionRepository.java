package com.aydsii.TP2_LAMOTH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aydsii.TP2_LAMOTH.model.HistorialConversion;

public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Long> {
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
            String monedaOrigen, String monedaDestino);
}
