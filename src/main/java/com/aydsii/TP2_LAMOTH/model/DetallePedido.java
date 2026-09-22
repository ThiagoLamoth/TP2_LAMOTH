package com.aydsii.TP2_LAMOTH.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_pedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public ProductoEntity getProducto() { return producto; }
    public Integer getCantidad() { return cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }

}
