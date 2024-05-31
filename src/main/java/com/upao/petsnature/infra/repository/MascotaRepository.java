package com.upao.petsnature.infra.repository;

import com.upao.petsnature.domain.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByUsuarioId(Long usuarioId);
    List<Mascota> findByRazaId(Long razaId);
    Optional<Mascota> findByNombreAndRazaTipoMascotaNombre(String nombre, String tipoMascotaNombre);
}
