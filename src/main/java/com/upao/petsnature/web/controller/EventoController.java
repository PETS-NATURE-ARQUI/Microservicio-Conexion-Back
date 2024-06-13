package com.upao.petsnature.web.controller;

import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;
import com.upao.petsnature.services.EventoProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<?> registrarEvento(@RequestBody DatosRegistroEvento datos) {
        try {
            eventoProxyService.registrarEvento(datos);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DatosDetallesEvento>> listarEventos(@RequestParam String nombreMascota) {
        try {
            List<DatosDetallesEvento> eventos = eventoProxyService.obtenerEventosPorMascota(nombreMascota);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            System.out.println("Error al listar los eventos: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEvento(@PathVariable Long id) {
        try {
            eventoProxyService.eliminarEvento(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar-fecha/{eventoId}")
    public ResponseEntity<?> actualizarFechaEvento(@PathVariable Long eventoId, @RequestBody Map<String, String> nuevaFechaMap) {
        try {
            String nuevaFechaStr = nuevaFechaMap.get("fecha");
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);
            eventoProxyService.actualizarFechaEvento(eventoId, nuevaFecha);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal(); // El id del usuario es el principal
    }
}
