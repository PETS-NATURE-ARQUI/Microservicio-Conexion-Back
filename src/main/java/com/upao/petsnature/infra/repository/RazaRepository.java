package com.upao.petsnature.infra.repository;

import com.upao.petsnature.domain.entity.Raza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RazaRepository extends JpaRepository<Raza, Long> {
    @Query("SELECT r FROM Raza r WHERE LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Raza> buscarRazasPorNombre(@Param("nombre") String nombre);
}