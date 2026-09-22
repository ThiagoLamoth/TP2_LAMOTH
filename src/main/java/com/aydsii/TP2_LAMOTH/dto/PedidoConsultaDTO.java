package com.aydsii.TP2_LAMOTH.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoConsultaDTO {
    private Long pedidoId;
    private String cliente;
    private LocalDate fecha;
    private String estado;
    private BigDecimal totalPedido;
    private List<ProductoPedidoDTO> productos;

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getTotalPedido() { return totalPedido; }
    public void setTotalPedido(BigDecimal totalPedido) { this.totalPedido = totalPedido; }
    public List<ProductoPedidoDTO> getProductos() { return productos; }
    public void setProductos(List<ProductoPedidoDTO> productos) { this.productos = productos; }
}
