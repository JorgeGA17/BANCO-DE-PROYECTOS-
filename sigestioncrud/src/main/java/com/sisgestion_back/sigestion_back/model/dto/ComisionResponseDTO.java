package com.sisgestion_back.sigestion_back.model.dto;

import com.sisgestion_back.sigestion_back.model.entity.Corte;
import com.sisgestion_back.sigestion_back.model.entity.Periodo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComisionResponseDTO {

    private Long comisionPk;
    private String xdescripcion;


    // Campos para relaciones ManyToOne
    private Long corteId;
    private String nombreCorte;
    private Long periodoId;
    private String nombrePeriodo;
}
