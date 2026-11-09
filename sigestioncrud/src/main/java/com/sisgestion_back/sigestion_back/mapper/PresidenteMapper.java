package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.*;
import com.sisgestion_back.sigestion_back.model.entity.Presidente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PresidenteMapper {
    private final ModelMapper modelMapper;

    public Presidente convertToEntity(PresidenteRequestDTO presidenteRequestDTO) {
        return modelMapper.map(presidenteRequestDTO, Presidente.class);
    }

    public PresidenteResponseDTO convertToDTO(Presidente presidente) {
        PresidenteResponseDTO presidenteResponseDTO = modelMapper.map(presidente, PresidenteResponseDTO.class);

        if (presidente.getPeriodofk() != null) {
            presidenteResponseDTO.setPeriodoId(presidente.getPeriodofk().getPeriodoPk());
            presidenteResponseDTO.setPeriodoNombre(presidente.getPeriodofk().getXnombre());
        } else {
            presidenteResponseDTO.setPeriodoId(null);
            presidenteResponseDTO.setPeriodoNombre(null);
        }

        if (presidente.getCortefk() != null) {
            presidenteResponseDTO.setCorteId(presidente.getCortefk().getCortePk());
            presidenteResponseDTO.setCorteNombre(presidente.getCortefk().getXnombreCorto());
        } else {
            presidenteResponseDTO.setCorteId(null);
            presidenteResponseDTO.setCorteNombre(null);
        }

        if (presidente.getPersonalfk() != null) {
           presidenteResponseDTO.setPersonalId(presidente.getPersonalfk().getPersonalPk());
            presidenteResponseDTO.setPersonalNombre(presidente.getPersonalfk().getXnombreCompleto());
        } else {
            presidenteResponseDTO.setPersonalId(null);
            presidenteResponseDTO.setPersonalNombre(null);
        }

        return presidenteResponseDTO;

    }

    public List<PresidenteResponseDTO> convertToDTO(List<Presidente> presidentes) {

        return presidentes.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
