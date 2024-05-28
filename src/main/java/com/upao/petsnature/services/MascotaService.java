package com.upao.petsnature.services;

import com.upao.petsnature.domain.dto.mascotaDto.DatosActualizarMascota;
import com.upao.petsnature.domain.dto.mascotaDto.DatosRegistrarMascota;
import com.upao.petsnature.domain.entity.Mascota;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MascotaService {

    void agregarMascota(DatosRegistrarMascota datos);

    String guardarImagen(MultipartFile imagen) throws IOException;

    Mascota modificarMascota(Long mascotaId, DatosActualizarMascota datos);

    List<Mascota> obtenerMascotasPorUsuario();

    List<Mascota> obtenerMascotasPorRaza(Long razaId);
}
