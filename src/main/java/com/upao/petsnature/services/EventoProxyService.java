package com.upao.petsnature.services;

import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EventoProxyService {

    @Value("${evento.base-url}")
    private String baseUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void registrarEvento(String authorization, DatosRegistroEvento datos) {
        webClientBuilder.build()
                .post()
                .uri(baseUrl)
                .header("Authorization", authorization)
                .body(Mono.just(datos), DatosRegistroEvento.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<DatosDetallesEvento> obtenerEventosPorMascota(String authorization, String nombreMascota) {
        String uri = baseUrl + "?nombreMascota=" + nombreMascota;
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .header("Authorization", authorization)
                .retrieve()
                .bodyToFlux(DatosDetallesEvento.class)
                .collectList()
                .block();
    }

    public void eliminarEvento(String authorization, String eventoId) {
        webClientBuilder.build()
                .method(HttpMethod.DELETE)
                .uri(baseUrl)
                .header("Authorization", authorization)
                .body(Mono.just(Map.of("eventoId", eventoId)), Map.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void actualizarFechaEvento(String authorization, String eventoId, LocalDate nuevaFecha) {
        webClientBuilder.build()
                .put()
                .uri(baseUrl)
                .header("Authorization", authorization)
                .body(Mono.just(Map.of("eventoId", eventoId, "nuevaFecha", nuevaFecha.toString())), Map.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

