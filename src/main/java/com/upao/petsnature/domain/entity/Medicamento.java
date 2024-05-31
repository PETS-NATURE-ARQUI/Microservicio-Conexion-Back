package com.upao.petsnature.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medicamento extends Complemento {
    private String dosis;
    private String frecuencia;
}
