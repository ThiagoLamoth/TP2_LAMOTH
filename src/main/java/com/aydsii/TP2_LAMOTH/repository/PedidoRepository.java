package com.aydsii.TP2_LAMOTH.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aydsii.TP2_LAMOTH.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("""
        SELECT DISTINCT p FROM Pedido p
        LEFT JOIN p.cliente c
        LEFT JOIN p.detalles d
        LEFT JOIN d.producto pr
        LEFT JOIN pr.categoria cat
        WHERE (:clienteId IS NULL OR c.id = :clienteId)
          AND (:categoria IS NULL OR cat.nombre = :categoria)
          AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde)
          AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta)
          AND (:estado IS NULL OR p.estado = :estado)
        """)
    List<Pedido> buscar(
            @Param("clienteId") Long clienteId,
            @Param("categoria") String categoria,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estado") String estado);

}
