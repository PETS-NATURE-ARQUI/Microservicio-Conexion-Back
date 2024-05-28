package com.upao.petsnature.domain.dto.eventoDto;

import java.time.LocalDate;

public record DatosRegistroEvento(
        LocalDate fecha,
        String veterinaria,
        String descripcion,
        String costo,
        String tipoEvento,
        String archivo,
        Long mascotaId,
        String nombreComplemento,
        String descripcionComplemento,
        String tipoMedicamento,
        LocalDate fechaMedicamento
) {
}
