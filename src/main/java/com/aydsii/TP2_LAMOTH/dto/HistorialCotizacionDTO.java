package com.aydsii.TP2_LAMOTH.dto;

import java.time.LocalDateTime;

public class HistorialCotizacionDTO {
    private LocalDateTime fecha;
    private double tasaCambio;

    public HistorialCotizacionDTO(LocalDateTime fecha, double tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public LocalDateTime getFecha() { return fecha; }
    public double getTasaCambio() { return tasaCambio; }
}