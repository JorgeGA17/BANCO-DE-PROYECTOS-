package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PresidenteRequestDTO {

    @NotNull(message = "ID del periodo es requerido")
    @Positive(message = "El ID del periodo debe ser un número positivo")
    private Long periodoId;

    @NotNull(message = "ID de la corte es requerido")
    @Positive(message = "El ID de la corte debe ser un número positivo")
    private Long corteId;

    @NotNull(message = "ID del personal es requerido")
    @Positive(message = "El ID del personal debe ser un número positivo")
    private Long personalId;


}

