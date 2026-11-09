package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComisionRequestDTO {
    private String xdescripcion;

    @NotNull(message = "ID de la corte es requerido")
    @Positive(message = "El ID de la corte debe ser un número positivo")
    private Long corteId;

    @NotNull(message = "ID de la corte es requerido")
    @Positive(message = "El ID de la corte debe ser un número positivo")
    private Long periodoId;

}
