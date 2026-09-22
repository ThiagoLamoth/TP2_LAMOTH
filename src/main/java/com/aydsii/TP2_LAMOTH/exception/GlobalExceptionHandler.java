package com.aydsii.TP2_LAMOTH.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aydsii.TP2_LAMOTH.dto.RespuestaApi;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern PATRON_INDICE = Pattern.compile("\\[(\\d+)]\\.(.+)");

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespuestaApi<Object>> handleNotFound(ResourceNotFoundException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(404, ex.getMessage(), null);
        return ResponseEntity.status(404).body(respuesta);
    }

    @ExceptionHandler(ListaVaciaException.class)
    public ResponseEntity<RespuestaApi<Object>> handleListaVacia(ListaVaciaException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaApi<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();

        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }

        RespuestaApi<Map<String, String>> respuesta = new RespuestaApi<>(400, "Error de validacion", errores);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(Exception.class) // catch-all → 500
    public ResponseEntity<RespuestaApi<Object>> handleGeneral(Exception ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(500, "Error interno del servidor", null);
        return ResponseEntity.status(500).body(respuesta);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.HandlerMethodValidationException.class)
    public ResponseEntity<RespuestaApi<List<Map<String, Object>>>> handleListValidation(
        org.springframework.web.method.annotation.HandlerMethodValidationException ex) {

        List<Map<String, Object>> errores = new ArrayList<>();

        for (org.springframework.validation.method.ParameterErrors parametroErrors : ex.getBeanResults()) {
            Integer posicion = parametroErrors.getContainerIndex();
            for (FieldError fe : parametroErrors.getFieldErrors()) {
                Map<String, Object> error = new LinkedHashMap<>();
                if (posicion != null) {
                    error.put("posicion", posicion);
                }
                error.put("campo", fe.getField());
                error.put("motivo", fe.getDefaultMessage());
                errores.add(error);
            }
        }

        RespuestaApi<List<Map<String, Object>>> respuesta =
            new RespuestaApi<>(400, "Error de validación en la lista de ventas", errores);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(PorcentajeInvalidoException.class)
    public ResponseEntity<RespuestaApi<Object>> handlePorcentajeInvalido(PorcentajeInvalidoException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(StockInvalidoException.class)
    public ResponseEntity<RespuestaApi<Object>> handleStockInvalido(StockInvalidoException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(ParametrosInvalidosException.class)
    public ResponseEntity<RespuestaApi<Object>> handleParametrosInvalidos(ParametrosInvalidosException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(MonedaInvalidaException.class)
    public ResponseEntity<RespuestaApi<Object>> handleMonedaInvalida(MonedaInvalidaException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

    @ExceptionHandler(ServicioExternoException.class)
    public ResponseEntity<RespuestaApi<Object>> handleServicioExterno(ServicioExternoException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(502, ex.getMessage(), null);
        return ResponseEntity.status(502).body(respuesta);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    ResponseEntity<RespuestaApi<Object>> handleEmailDuplicado(EmailDuplicadoException ex) {
        RespuestaApi<Object> respuesta = new RespuestaApi<>(400, ex.getMessage(), null);
        return ResponseEntity.status(400).body(respuesta);
    }

}