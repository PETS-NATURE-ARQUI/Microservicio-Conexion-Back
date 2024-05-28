package com.upao.petsnature.domain.dto.eventoDto;

import java.time.LocalDate;

public record DatosDetallesEvento(
    Long id,
    LocalDate fecha,
    String veterinaria,
    String descripcion,
    String costo,
    String tipoEvento,
    String archivo,
    String nombreMascota,
    String complemento
) {
}
