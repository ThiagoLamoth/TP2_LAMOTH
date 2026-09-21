package com.aydsii.TP2_LAMOTH.service;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.aydsii.TP2_LAMOTH.dto.ConversionDTO;
import com.aydsii.TP2_LAMOTH.dto.FrankfurterResponseDTO;
import com.aydsii.TP2_LAMOTH.exception.MonedaInvalidaException;
import com.aydsii.TP2_LAMOTH.exception.ServicioExternoException;

@Service
public class DivisaService {

    private final RestClient restClient;

    public DivisaService() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // 5 segundos
        requestFactory.setReadTimeout(5000);

        this.restClient = RestClient.builder()
                .baseUrl("https://api.frankfurter.app")
                .requestFactory(requestFactory)
                .build();
    }

    public ConversionDTO convertir(double monto, String origen, String destino) {
        FrankfurterResponseDTO respuestaExterna;

        try {
            respuestaExterna = restClient.get()
                    .uri("/latest?amount={monto}&from={origen}&to={destino}", monto, origen, destino)
                    .retrieve()
                    .body(FrankfurterResponseDTO.class);
        } catch (HttpClientErrorException ex) {
            // Frankfurter rechazó los datos (por ejemplo, moneda inexistente)
            throw new MonedaInvalidaException("La moneda indicada no es válida o no está soportada por el servicio de conversión");
        } catch (HttpServerErrorException | ResourceAccessException ex) {
            // Frankfurter no responde, da timeout, o falla internamente
            throw new ServicioExternoException("No se pudo obtener la información del servicio de conversión");
        }

        if (respuestaExterna == null || respuestaExterna.getRates() == null
                || !respuestaExterna.getRates().containsKey(destino)) {
            throw new ServicioExternoException("El servicio de conversión no devolvió datos válidos");
        }

        double montoConvertido = respuestaExterna.getRates().get(destino);
        double tasaCambio = montoConvertido / monto;

        ConversionDTO conversion = new ConversionDTO();
        conversion.setMontoOriginal(monto);
        conversion.setMonedaOrigen(origen);
        conversion.setMonedaDestino(destino);
        conversion.setTasaCambio(tasaCambio);
        conversion.setMontoConvertido(montoConvertido);
        conversion.setFecha(respuestaExterna.getDate());

        return conversion;
    }
}
