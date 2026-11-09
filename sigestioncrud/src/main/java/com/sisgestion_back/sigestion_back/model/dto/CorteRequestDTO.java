package com.sisgestion_back.sigestion_back.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CorteRequestDTO {

    private String xnombre;
    private String xnombreCorto;


}
