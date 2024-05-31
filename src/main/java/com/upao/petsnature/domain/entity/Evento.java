package com.upao.petsnature.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Evento")
@Table(name = "eventos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_evento", columnDefinition = "DATE")
    private LocalDate fecha;
    private String veterinaria;
    private String descripcion;
    private String costo;
    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;
    @Lob
    @Column(length = 10485760) // 10MB
    private String archivo; // word, pdf, excel, jpg, png
    private LocalDate modificadoFecha;
    @Column(nullable = false, name = "estado")
    private boolean enabled = true;
    @ManyToOne(fetch = FetchType.LAZY)
    private Mascota mascota;
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Complemento> complemento = new HashSet<>();

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
        this.modificadoFecha = LocalDate.now(); // También actualiza la fecha de modificación
    }


}
