package com.upao.petsnature.services.impl;

import com.upao.petsnature.domain.dto.eventoDto.DatosDetallesEvento;
import com.upao.petsnature.domain.dto.eventoDto.DatosRegistroEvento;
import com.upao.petsnature.domain.entity.*;
import com.upao.petsnature.infra.repository.EventoRepository;
import com.upao.petsnature.infra.repository.MascotaRepository;
import com.upao.petsnature.infra.repository.ComplementoRepository;
import com.upao.petsnature.infra.repository.UsuarioRepository;
import com.upao.petsnature.services.EventoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ComplementoRepository complementoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void registrarEvento(DatosRegistroEvento datos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        Mascota mascota = mascotaRepository.findByNombreAndRazaTipoMascotaNombre(datos.nombreMascota(), datos.tipoMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Evento evento = new Evento();
        evento.setFecha(datos.fecha());
        evento.setVeterinaria(datos.veterinaria());
        evento.setDescripcion(datos.descripcion());
        evento.setCosto(datos.costo());
        evento.setTipo(TipoEvento.valueOf(datos.tipoEvento()));
        evento.setArchivo(datos.archivo());
        evento.setMascota(mascota);

        evento = eventoRepository.save(evento);

        if (datos.nombreComplemento() != null && !datos.nombreComplemento().isEmpty()) {
            Complemento complemento;
            if ("VACUNA".equalsIgnoreCase(datos.tipoComplemento())) {
                complemento = new Vacuna();
                ((Vacuna) complemento).setFabricante(datos.fabricante());
                ((Vacuna) complemento).setLote(datos.lote());
            } else if ("MEDICAMENTO".equalsIgnoreCase(datos.tipoComplemento())) {
                complemento = new Medicamento();
                ((Medicamento) complemento).setDosis(datos.dosis());
                ((Medicamento) complemento).setFrecuencia(datos.frecuencia());
            } else {
                throw new RuntimeException("Tipo de complemento no válido: " + datos.tipoComplemento());
            }
            complemento.setNombre(datos.nombreComplemento());
            complemento.setDescripcion(datos.descripcionComplemento());
            if (datos.fechaComplemento() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime fechaComplemento = LocalDateTime.parse(datos.fechaComplemento() + "T00:00:00", formatter);
                complemento.setFecha(fechaComplemento.toLocalDate());
            }
            complemento.setEvento(evento);

            complementoRepository.save(complemento);
        }
    }

    @Override
    public List<DatosDetallesEvento> obtenerEventosPorUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        List<Mascota> mascotas = mascotaRepository.findByUsuarioId(usuario.getId());
        List<Evento> eventos = eventoRepository.findByMascotaIn(mascotas);

        return eventos.stream()
                .map(evento -> new DatosDetallesEvento(
                        evento.getFecha(),
                        evento.getVeterinaria(),
                        evento.getDescripcion(),
                        evento.getCosto(),
                        evento.getTipo().name(),
                        evento.getArchivo(),
                        evento.getMascota().getNombre(),
                        evento.getComplemento().stream().findFirst().map(Complemento::getNombre).orElse(null)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado: " + eventoId));
        eventoRepository.delete(evento);
    }

    @Override
    public void actualizarFechaEvento(Long eventoId, LocalDate nuevaFecha) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado: " + eventoId));
        evento.setFecha(nuevaFecha);
        evento.setModificadoFecha(LocalDate.now()); // Registrar la fecha de modificación
        eventoRepository.save(evento);
    }

}

