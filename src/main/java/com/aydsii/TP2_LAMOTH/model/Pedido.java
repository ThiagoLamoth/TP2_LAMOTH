package com.aydsii.TP2_LAMOTH.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    private String estado;

    // fetch = EAGER para que la lista de detalles venga cargada completa,
    // sin importar cómo haya filtrado la consulta principal
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<DetallePedido> detalles;

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public LocalDate getFechaPedido() { return fechaPedido; }
    public String getEstado() { return estado; }
    public List<DetallePedido> getDetalles() { return detalles; }

}
