package com.upao.petsnature.web.controller;

import com.upao.petsnature.domain.entity.Raza;
import com.upao.petsnature.services.RazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/razas")
@CrossOrigin("*")
public class RazaController {
    @Autowired
    private RazaService razaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Raza>> listarRazas() {
        List<Raza> razas = razaService.obtenerTodasLasRazas();
        return ResponseEntity.ok(razas);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Raza>> buscarRazas(@RequestParam String nombre) {
        List<Raza> razas = razaService.buscarRazasPorNombre(nombre);
        return ResponseEntity.ok(razas);
    }
}
