package com.upao.petsnature.services;

import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;

import java.time.LocalDate;
import java.util.List;

public interface EventoService {

    void registrarEvento(DatosRegistroEvento datos);

    /*List<DatosDetallesEvento> obtenerEventosPorUsuario();*/

    void eliminarEvento(Long eventoId);

    void actualizarFechaEvento(Long eventoId, LocalDate nuevaFecha);
}
