package com.aydsii.TP2_LAMOTH.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.TP2_LAMOTH.dto.ProductoDTO;
import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;
import com.aydsii.TP2_LAMOTH.model.Producto;
import com.aydsii.TP2_LAMOTH.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Catálogo", description = "Gestión de productos en memoria")
@RestController
@RequestMapping("/api/catalogo")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public ResponseEntity<RespuestaApi<List<Producto>>> listarTodos() {
        RespuestaApi<List<Producto>> respuesta =
            new RespuestaApi<>(200, "Productos obtenidos con éxito", productoService.listarTodos());
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar productos con filtros combinables")
    @GetMapping("/buscar")
    public ResponseEntity<RespuestaApi<List<Producto>>> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {

        List<Producto> resultado = productoService.buscar(categoria, precioMin, precioMax);
        RespuestaApi<List<Producto>> respuesta = new RespuestaApi<>(200, "Búsqueda realizada con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Ordenar productos por precio o nombre")
    @GetMapping("/ordenar")
    public ResponseEntity<RespuestaApi<List<Producto>>> ordenar(
            @RequestParam String criterio,
            @RequestParam(defaultValue = "asc") String orden) {

        List<Producto> resultado = productoService.ordenar(criterio, orden);
        RespuestaApi<List<Producto>> respuesta = new RespuestaApi<>(200, "Productos ordenados con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<RespuestaApi<Producto>> crear(@RequestBody @Valid ProductoDTO dto) {
        Producto creado = productoService.crear(dto);
        RespuestaApi<Producto> respuesta = new RespuestaApi<>(201, "Producto creado con éxito", creado);
        return ResponseEntity.status(201).body(respuesta);
    }

    @Operation(summary = "Modificar el stock de un producto")
    @PutMapping("/{id}/stock")
    public ResponseEntity<RespuestaApi<Producto>> modificarStock(
            @PathVariable Long id,
            @RequestParam int cantidad) {

        Producto actualizado = productoService.modificarStock(id, cantidad);
        RespuestaApi<Producto> respuesta = new RespuestaApi<>(200, "Stock actualizado con éxito", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaApi<Object>> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        RespuestaApi<Object> respuesta = new RespuestaApi<>(200, "Producto eliminado con éxito", null);
        return ResponseEntity.ok(respuesta);
    }
} 