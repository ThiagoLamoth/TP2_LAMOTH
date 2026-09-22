package com.aydsii.TP2_LAMOTH.service;
import com.aydsii.TP2_LAMOTH.dto.PedidoConsultaDTO;
import com.aydsii.TP2_LAMOTH.dto.ProductoPedidoDTO;
import com.aydsii.TP2_LAMOTH.model.Pedido;
import com.aydsii.TP2_LAMOTH.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoConsultaDTO> buscar(Long clienteId, String categoria, LocalDate fechaDesde, LocalDate fechaHasta, String estado) {
        List<Pedido> pedidos = pedidoRepository.buscar(clienteId, categoria, fechaDesde, fechaHasta, estado);
        return pedidos.stream().map(this::convertir).collect(Collectors.toList());
    }

    private PedidoConsultaDTO convertir(Pedido pedido) {
        List<ProductoPedidoDTO> productos = pedido.getDetalles().stream()
                .map(d -> new ProductoPedidoDTO(
                        d.getProducto().getNombre(),
                        d.getProducto().getCategoria() != null ? d.getProducto().getCategoria().getNombre() : null,
                        d.getCantidad(),
                        d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad()))))
                .collect(Collectors.toList());

        BigDecimal total = productos.stream()
                .map(ProductoPedidoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PedidoConsultaDTO dto = new PedidoConsultaDTO();
        dto.setPedidoId(pedido.getId());
        dto.setCliente(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido());
        dto.setFecha(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotalPedido(total);
        dto.setProductos(productos);
        return dto;
    }
}
