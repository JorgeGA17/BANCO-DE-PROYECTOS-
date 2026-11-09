package com.sisgestion_back.sigestion_back.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalRequestDTO {

    private String xtipoDocumento;
    private String nnumeroDocumento;
    private String xnombre;
    private String xapellido;
    private String xocupacion;
    private String xcorreoInstitucional;

}
