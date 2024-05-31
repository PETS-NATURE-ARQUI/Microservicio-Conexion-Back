package com.upao.petsnature.domain.dto.eventoDto;

import java.time.LocalDate;

public record DatosRegistroEvento(
        LocalDate fecha,
        String veterinaria,
        String descripcion,
        String costo,
        String tipoEvento,
        String archivo,
        String nombreMascota,
        String tipoMascota,
        String nombreComplemento,
        String descripcionComplemento,
        String tipoComplemento,
        LocalDate fechaComplemento,
        // Campos específicos para Vacuna
        String fabricante,
        String lote,
        // Campos específicos para Medicamento
        String dosis,
        String frecuencia
) {
}
