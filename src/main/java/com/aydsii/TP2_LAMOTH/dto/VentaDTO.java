package com.aydsii.TP2_LAMOTH.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class VentaDTO {

    //@NotBlank rechaza tanto null como cadenas vacias o solo espacios
    @NotBlank(message = "El producto no puede estar vacio")
    private String producto;

    //@Min(1) expresa un entero positvio
    @Min(value = 1, message = "La cantidad debe ser un entero positivo mayor a 0")
    private int cantidad;

    //@positive cubre "mayor a 0" sin necesitar un valor límite fijo.
    @Positive(message = "El precio unitario debe ser positivo, mayor a 0")
    private double precioUnitario;

    // getters y setters

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}