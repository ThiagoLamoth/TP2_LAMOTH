package com.aydsii.TP2_LAMOTH.dto;

public class VentaConImporteDTO {
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double importe;

    public VentaConImporteDTO(String producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.importe = cantidad * precioUnitario;
    }

    // getters (no hacen falta setters, se arma solo por constructor)
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getImporte() { return importe; }
}