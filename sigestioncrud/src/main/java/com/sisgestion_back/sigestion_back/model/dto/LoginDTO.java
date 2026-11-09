package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @Email(message = "el correo no es valido")
    @NotBlank(message = "el correo es obligatorio")
    private String xcorreoInstitucional;

    @NotBlank(message = "la contraseña es obligatoria")
    private String password;
}
