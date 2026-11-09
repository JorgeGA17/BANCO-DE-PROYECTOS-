package com.sisgestion_back.sigestion_back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiembroResponseDTO {
    private Long miembroPk;

    // Campos para relaciones ManyToOne
    private Long comisionId;
    private String comisionNombreCorte;

    private Long personalId;
    private String personalNombre;
    private String personalCorreo;

    private Long cargoId;
    private String cargoNombre;


}
