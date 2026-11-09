package com.sisgestion_back.sigestion_back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoResponseDTO {

    private Long proyectoPk;
    private String xnombreProyecto;
    private String xproblematica;
    private String xresumen;
    private String xobjetivoGeneral;
    private Integer xinnovacion;
    private Integer ximpacto;
    private Integer xsostenibilidad;
    private Integer xreplicabilidad;
    private Integer npeso;
    private String xconceptoEval;
    private String xdescripEval;

    // Campos para relaciones ManyToOne
    private Long corteId;
    private String nombreCorte;
    private Long estadoId;
    private String nombreEstado;

    // IDs de las colecciones ManyToMany
    private List<Long> especialidadIds;
    private List<Long> jerarquiaIds;
    private List<Long> ejeIds;
    private List<Long> etiquetaIds;
    private List<Long> personalIds;

    // Nombres/Descripciones de las colecciones ManyToMany
    private List<String> listaNombresEspecialidades;
    private List<String> listaNombresJerarquias;
    private List<String> listaNombresEjes;
    private List<String> listaNombresEtiquetas;
    private List<String> listaNombresPersonal;
}