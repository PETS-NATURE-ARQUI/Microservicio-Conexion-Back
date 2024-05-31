package com.upao.petsnature.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vacuna extends Complemento {
    private String fabricante;
    private String lote;
}
