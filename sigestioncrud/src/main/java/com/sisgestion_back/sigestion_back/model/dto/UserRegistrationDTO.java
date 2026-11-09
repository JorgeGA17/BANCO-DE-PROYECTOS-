package com.sisgestion_back.sigestion_back.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    @NotBlank (message = "El nombre es obligatorio")
    private String xnombre;

    @NotBlank (message = "El apellido es obligatorio")
    private String xapellido;

    @Email (message = "El correo electronico no es valido")
    @NotBlank (message = "El correo es obligatorio")
    private String xcorreoInstitucional;

    @NotBlank (message = "El DNI es obligatorio")
    private String nnumeroDocumento;

    @NotBlank (message = "La ocupacion es obligatorio")
    private String xocupacion;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min=4, message = "la contraseña debe tener al menos 4 digitos")
    private String password;
}
