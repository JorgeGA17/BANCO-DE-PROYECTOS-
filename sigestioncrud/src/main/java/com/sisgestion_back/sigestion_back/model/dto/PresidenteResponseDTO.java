package com.sisgestion_back.sigestion_back.model.dto;

import com.sisgestion_back.sigestion_back.model.entity.Corte;
import com.sisgestion_back.sigestion_back.model.entity.Periodo;
import com.sisgestion_back.sigestion_back.model.entity.Personal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresidenteResponseDTO {

    private Long presidentePk;

    // Campos para relaciones ManyToOne
    private Long periodoId;
    private String periodoNombre;

    private Long corteId;
    private String corteNombre;

    private Long personalId;
    private String personalNombre;



}
