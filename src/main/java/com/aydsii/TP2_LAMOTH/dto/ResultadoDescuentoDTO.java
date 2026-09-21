package com.aydsii.TP2_LAMOTH.dto;
import java.util.List;

public class ResultadoDescuentoDTO {
    private List<VentaConDescuentoDTO> ventas;
    private double totalConDescuento;

    public List<VentaConDescuentoDTO> getVentas() { return ventas; }
    public void setVentas(List<VentaConDescuentoDTO> ventas) { this.ventas = ventas; }

    public double getTotalConDescuento() { return totalConDescuento; }
    public void setTotalConDescuento(double totalConDescuento) { this.totalConDescuento = totalConDescuento; }

}
