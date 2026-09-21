package com.aydsii.TP2_LAMOTH.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aydsii.TP2_LAMOTH.dto.EstadisticasVentaDTO;
import com.aydsii.TP2_LAMOTH.dto.ResultadoDescuentoDTO;
import com.aydsii.TP2_LAMOTH.dto.VentaConDescuentoDTO;
import com.aydsii.TP2_LAMOTH.dto.VentaConImporteDTO;
import com.aydsii.TP2_LAMOTH.dto.VentaDTO;

@Service
public class VentaService {

    public EstadisticasVentaDTO calcularEstadisticas(List<VentaDTO> ventas) {
        double totalFacturado = 0;
        Map<String, Integer> cantidadPorProducto = new HashMap<>();

        VentaDTO ventaMayor = ventas.get(0);
        VentaDTO ventaMenor = ventas.get(0);
        double importeMayor = importeDe(ventaMayor);
        double importeMenor = importeMayor;

        for (VentaDTO venta : ventas) {
            double importe = importeDe(venta);
            totalFacturado += importe;

            // acumula cantidad por producto (aunque se repita en varias ventas)
            cantidadPorProducto.merge(venta.getProducto(), venta.getCantidad(), Integer::sum);

            if (importe > importeMayor) {
                importeMayor = importe;
                ventaMayor = venta;
            }
            if (importe < importeMenor) {
                importeMenor = importe;
                ventaMenor = venta;
            }
        }

        String productoMasVendido = cantidadPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        EstadisticasVentaDTO estadisticas = new EstadisticasVentaDTO();
        estadisticas.setTotalFacturado(totalFacturado);
        estadisticas.setCantidadVentas(ventas.size());
        estadisticas.setTicketPromedio(totalFacturado / ventas.size());
        estadisticas.setVentaMayor(new VentaConImporteDTO(ventaMayor.getProducto(), ventaMayor.getCantidad(), ventaMayor.getPrecioUnitario()));
        estadisticas.setVentaMenor(new VentaConImporteDTO(ventaMenor.getProducto(), ventaMenor.getCantidad(), ventaMenor.getPrecioUnitario()));
        estadisticas.setProductoMasVendido(productoMasVendido);

        return estadisticas;
    }

    private double importeDe(VentaDTO venta) {
        return venta.getCantidad() * venta.getPrecioUnitario();
    }

    public ResultadoDescuentoDTO aplicarDescuento(List<VentaDTO> ventas, double porcentaje) {
    List<VentaConDescuentoDTO> ventasConDescuento = new ArrayList<>();
    double totalConDescuento = 0;

    for (VentaDTO venta : ventas) {
        double importe = venta.getCantidad() * venta.getPrecioUnitario();
        double montoConDescuento = importe * (1 - porcentaje / 100);

        ventasConDescuento.add(new VentaConDescuentoDTO(
                venta.getProducto(), venta.getCantidad(), venta.getPrecioUnitario(), montoConDescuento));

        totalConDescuento += montoConDescuento;
    }

    ResultadoDescuentoDTO resultado = new ResultadoDescuentoDTO();
    resultado.setVentas(ventasConDescuento);
    resultado.setTotalConDescuento(totalConDescuento);
    return resultado;
    }
}