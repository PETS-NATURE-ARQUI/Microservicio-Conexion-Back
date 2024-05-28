package com.upao.petsnature.infra.repository;

import com.upao.petsnature.domain.entity.Evento;
import com.upao.petsnature.domain.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByMascotaIn(List<Mascota> mascotas);
}
