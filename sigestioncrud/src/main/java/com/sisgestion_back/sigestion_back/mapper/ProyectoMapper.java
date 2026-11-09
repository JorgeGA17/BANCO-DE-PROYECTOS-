package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.*;
import com.sisgestion_back.sigestion_back.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProyectoMapper {

    private final ModelMapper modelMapper;

    // --- Métodos Auxiliares Privados para mapear colecciones a List<String> de nombres ---

    private List<String> mapEspecialidadesToNames(List<Especialidad> especialidades) {
        return Optional.ofNullable(especialidades)
                .orElse(Collections.emptyList())
                .stream()
                .map(Especialidad::getXnombre)
                .collect(Collectors.toList());
    }

    private List<String> mapJerarquiasToNames(List<Jerarquia> jerarquias) {
        return Optional.ofNullable(jerarquias)
                .orElse(Collections.emptyList())
                .stream()
                .map(Jerarquia::getXnombre)
                .collect(Collectors.toList());
    }

    private List<String> mapEjesToNames(List<Eje> ejes) {
        return Optional.ofNullable(ejes)
                .orElse(Collections.emptyList())
                .stream()
                .map(Eje::getXnombre)
                .collect(Collectors.toList());
    }

    private List<String> mapEtiquetasToNames(List<Etiqueta> etiquetas) {
        return Optional.ofNullable(etiquetas)
                .orElse(Collections.emptyList())
                .stream()
                .map(Etiqueta::getXnombre)
                .collect(Collectors.toList());
    }

    private List<String> mapPersonalToFullNames(List<Personal> personal) {
        return Optional.ofNullable(personal)
                .orElse(Collections.emptyList())
                .stream()
                .map(Personal::getXnombreCompleto)
                .collect(Collectors.toList());
    }

    // --- Método Principal de Conversión a DTO de Respuesta ---

    public ProyectoResponseDTO convertToDTO(Proyecto proyecto) {
        ProyectoResponseDTO proyectoResponseDTO = modelMapper.map(proyecto, ProyectoResponseDTO.class);

        // Mapeo para relaciones ManyToOne (Corte y Estado)
        if (proyecto.getCortefk() != null) {
            proyectoResponseDTO.setCorteId(proyecto.getCortefk().getCortePk());
            proyectoResponseDTO.setNombreCorte(proyecto.getCortefk().getXnombreCorto());
        } else {
            proyectoResponseDTO.setCorteId(null);
            proyectoResponseDTO.setNombreCorte(null);
        }

        if (proyecto.getEstadofk() != null) {
            proyectoResponseDTO.setEstadoId(proyecto.getEstadofk().getEstadoPk());
            proyectoResponseDTO.setNombreEstado(proyecto.getEstadofk().getXnombre());
        } else {
            proyectoResponseDTO.setEstadoId(null);
            proyectoResponseDTO.setNombreEstado(null);
        }

        // Mapeo de IDs para relaciones ManyToMany
        proyectoResponseDTO.setEspecialidadIds(
                Optional.ofNullable(proyecto.getEspecialidades())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Especialidad::getEspecialidadPk)
                        .collect(Collectors.toList())
        );
        proyectoResponseDTO.setJerarquiaIds(
                Optional.ofNullable(proyecto.getJerarquias())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Jerarquia::getJerarquiaPk)
                        .collect(Collectors.toList())
        );
        proyectoResponseDTO.setEjeIds(
                Optional.ofNullable(proyecto.getEjes())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Eje::getEjePk)
                        .collect(Collectors.toList())
        );
        proyectoResponseDTO.setEtiquetaIds(
                Optional.ofNullable(proyecto.getEtiquetas())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Etiqueta::getEtiquetaPk)
                        .collect(Collectors.toList())
        );
        proyectoResponseDTO.setPersonalIds(
                Optional.ofNullable(proyecto.getPersonal())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Personal::getPersonalPk)
                        .collect(Collectors.toList())
        );

        // Mapeo de NOMBRES de colecciones ManyToMany usando los métodos auxiliares
        proyectoResponseDTO.setListaNombresEspecialidades(mapEspecialidadesToNames(proyecto.getEspecialidades()));
        proyectoResponseDTO.setListaNombresJerarquias(mapJerarquiasToNames(proyecto.getJerarquias()));
        proyectoResponseDTO.setListaNombresEjes(mapEjesToNames(proyecto.getEjes()));
        proyectoResponseDTO.setListaNombresEtiquetas(mapEtiquetasToNames(proyecto.getEtiquetas()));
        proyectoResponseDTO.setListaNombresPersonal(mapPersonalToFullNames(proyecto.getPersonal()));

        return proyectoResponseDTO;
    }

    // --- Conversión a Entidad desde DTO de Solicitud ---
    public Proyecto convertToEntity(ProyectoRequestDTO proyectoRequestDTO) {
        return modelMapper.map(proyectoRequestDTO, Proyecto.class);
    }

    // --- Conversión de Lista de Entidades a Lista de DTOs ---
    public List<ProyectoResponseDTO> convertToDTO(List<Proyecto> proyectos) {
        return proyectos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}