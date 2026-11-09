package com.sisgestion_back.sigestion_back.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoRequestDTO {

    @NotBlank(message = "Nombre proyecto es requerido")
    private String xnombreProyecto;

    @NotBlank(message = "Problematica es requerido")
    private String xproblematica;

    @NotBlank(message = "Resumen es requerido")
    private String xresumen;

    @NotBlank(message = "Objetivo General es requerido")
    private String xobjetivoGeneral;

    @NotNull(message = "Calificación de innovacion es requerido")
    @Positive(message = "La calificación de innovación debe ser un número positivo")
    private Integer xinnovacion;

    @NotNull(message = "Calificación de impacto es requerido")
    @Positive(message = "La calificación de impacto debe ser un número positivo")
    private Integer ximpacto;

    @NotNull(message = "Calificación de sostenibilidad es requerido")
    @Positive(message = "La calificación de sostenibilidad debe ser un número positivo")
    private Integer xsostenibilidad;

    @NotNull(message = "Calificación de replicabilidad es requerido")
    @Positive(message = "La calificación de replicabilidad debe ser un número positivo")
    private Integer xreplicabilidad;

    @NotBlank(message = "Concepto de evaluación es requerido")
    private String xconceptoEval;

    @NotBlank(message = "Descripción de evaluación es requerida")
    private String xdescripEval;

    @NotNull(message = "ID de la corte es requerido")
    @Positive(message = "El ID de la corte debe ser un número positivo")
    private Long corteId;

    @NotNull(message = "ID del estado es requerido")
    @Positive(message = "El ID del estado debe ser un número positivo")
    private Long estadoId;

    @NotEmpty(message = "La lista de especialidades es requerida")
    private List<Long> especialidadIds;

    @NotEmpty(message = "La lista de jerarquías es requerida")
    private List<Long> jerarquiaIds;

    @NotEmpty(message = "La lista de ejes es requerida")
    private List<Long> ejeIds;

    @NotEmpty(message = "La lista de etiquetas es requerida")
    private List<Long> etiquetaIds;

    @NotEmpty(message = "La lista de personal es requerida")
    private List<Long> personalIds;
}