package com.upao.petsnature.web.controller;

import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;
import com.upao.petsnature.services.EventoProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/evento")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EventoController {

    @Autowired
    private EventoProxyService eventoProxyService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEvento(@RequestHeader(value = "Authorization") String authorization, @RequestBody DatosRegistroEvento datos) {
        try {
            eventoProxyService.registrarEvento(authorization, datos);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DatosDetallesEvento>> listarEventos(@RequestHeader(value = "Authorization") String authorization, @RequestParam String nombreMascota) {
        try {
            List<DatosDetallesEvento> eventos = eventoProxyService.obtenerEventosPorMascota(authorization, nombreMascota);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            System.out.println("Error al listar los eventos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarEvento(@RequestHeader(value = "Authorization") String authorization, @RequestBody Map<String, String> requestBody) {
        String eventoId = requestBody.get("eventoId");
        if (eventoId == null || eventoId.isEmpty()) {
            return new ResponseEntity<>("eventoId es requerido", HttpStatus.BAD_REQUEST);
        }

        try {
            eventoProxyService.eliminarEvento(authorization, eventoId);
            return ResponseEntity.ok().body(Map.of("message", "Evento eliminado exitosamente"));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizar-fecha/{eventoId}")
    public ResponseEntity<?> actualizarFechaEvento(@PathVariable String eventoId, @RequestBody Map<String, String> nuevaFechaMap) {
        try {
            String nuevaFechaStr = nuevaFechaMap.get("fecha");
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);
            eventoProxyService.actualizarFechaEvento(eventoId, nuevaFecha);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
