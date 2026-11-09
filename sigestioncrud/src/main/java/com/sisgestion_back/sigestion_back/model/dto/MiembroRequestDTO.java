package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiembroRequestDTO {
//falta registrar para miembro. Ids

    @NotNull(message = "ID de la comision es requerido")
    @Positive(message = "El ID de la comision debe ser un número positivo")
    private Long comisionId;

    @NotNull(message = "ID del personal es requerido")
    @Positive(message = "El ID del personal debe ser un número positivo")
    private Long personalId;

    @NotNull(message = "ID del cargo es requerido")
    @Positive(message = "El ID del cargo debe ser un número positivo")
    private Long cargoId;
}
