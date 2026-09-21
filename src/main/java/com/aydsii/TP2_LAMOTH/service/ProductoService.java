package com.aydsii.TP2_LAMOTH.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aydsii.TP2_LAMOTH.dto.ProductoDTO;
import com.aydsii.TP2_LAMOTH.exception.ResourceNotFoundException;
import com.aydsii.TP2_LAMOTH.exception.StockInvalidoException;
import com.aydsii.TP2_LAMOTH.model.Producto;

@Service
public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong();

    public ProductoService() {
        agregarInicial("Teclado mecanico", "Perifericos", 25000, 10);
        agregarInicial("Mouse inalambrico", "Perifericos", 8500, 20);
        agregarInicial("Monitor 24 pulgadas", "Monitores", 95000, 5);
        agregarInicial("Auriculares gamer", "Perifericos", 32000, 15);
        agregarInicial("Notebook 15 pulgadas", "Computadoras", 650000, 3);
        agregarInicial("Webcam Full HD", "Perifericos", 18000, 8);
        agregarInicial("Placa de video RTX", "Componentes", 480000, 4);
        agregarInicial("Memoria RAM 16GB", "Componentes", 35000, 12);
    }

    private void agregarInicial(String nombre, String categoria, double precio, int stock) {
        productos.add(new Producto(contadorId.incrementAndGet(), nombre, categoria, precio, stock));
    }

    public List<Producto> listarTodos() {
        return productos;
    }

    public List<Producto> buscar(String categoria, Double precioMin, Double precioMax) {
        return productos.stream()
                .filter(p -> categoria == null || p.getCategoria().equalsIgnoreCase(categoria))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> ordenar(String criterio, String orden) {
        Comparator<Producto> comparator = "nombre".equalsIgnoreCase(criterio)
                ? Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER)
                : Comparator.comparingDouble(Producto::getPrecio);

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return productos.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Producto crear(ProductoDTO dto) {
        Producto producto = new Producto(
                contadorId.incrementAndGet(), dto.getNombre(), dto.getCategoria(), dto.getPrecio(), dto.getStock());
        productos.add(producto);
        return producto;
    }

    public Producto modificarStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);
        int nuevoStock = producto.getStock() + cantidad;
        if (nuevoStock < 0) {
            throw new StockInvalidoException("El stock no puede quedar por debajo de 0");
        }
        producto.setStock(nuevoStock);
        return producto;
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productos.remove(producto);
    }

    private Producto buscarPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No existe un producto con id " + id));
    }
}
