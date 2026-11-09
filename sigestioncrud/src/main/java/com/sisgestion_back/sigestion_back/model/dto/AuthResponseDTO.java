package com.sisgestion_back.sigestion_back.model.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;
    private String xnombre;
    private String xapellido;

    private String role;

}
