package com.upao.petsnature.services;

import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EventoProxyService {

    private static final String BASE_URL = "https://qxhb1qbbn2.execute-api.us-east-2.amazonaws.com/prod/evento";

    @Autowired
    private WebClient.Builder webClientBuilder;

    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        System.out.println("JWT Token: " + token);
        return token;
    }


    public void registrarEvento(String authorization, DatosRegistroEvento datos) {
        webClientBuilder.build()
                .post()
                .uri(BASE_URL)
                .header("Authorization", authorization)
                .body(Mono.just(datos), DatosRegistroEvento.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    public List<DatosDetallesEvento> obtenerEventosPorMascota(String authorization, String nombreMascota) {
        String uri = BASE_URL + "?nombreMascota=" + nombreMascota;
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .header("Authorization", authorization)
                .retrieve()
                .bodyToFlux(DatosDetallesEvento.class)
                .collectList()
                .block();
    }

    public void eliminarEvento(Long eventoId) {
        webClientBuilder.build()
                .delete()
                .uri(BASE_URL + "/{id}", eventoId)
                .header("Authorization", "Bearer " + getJwtToken())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void actualizarFechaEvento(Long eventoId, LocalDate nuevaFecha) {
        webClientBuilder.build()
                .put()
                .uri(BASE_URL + "/{id}", eventoId)
                .header("Authorization", "Bearer " + getJwtToken())
                .body(Mono.just(Map.of("fecha", nuevaFecha.toString())), Map.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

