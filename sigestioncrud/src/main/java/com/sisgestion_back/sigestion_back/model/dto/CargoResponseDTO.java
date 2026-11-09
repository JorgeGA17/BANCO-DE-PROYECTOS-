package com.sisgestion_back.sigestion_back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoResponseDTO {
    private Long cargoPk;
    private String xnombre;



}
