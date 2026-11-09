package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordDTO {

    @NotBlank(message = "El correo institucional es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String xcorreoInstitucional;

}
