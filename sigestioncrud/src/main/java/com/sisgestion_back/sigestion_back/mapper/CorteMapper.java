package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.CorteRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CorteResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Corte;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component

public class CorteMapper {

    private final ModelMapper modelMapper;

    public Corte convertToEntity(CorteRequestDTO corteRequestDTO) {
        return modelMapper.map(corteRequestDTO, Corte.class);
    }

    public CorteResponseDTO convertToDTO(Corte corte) {
        return modelMapper.map(corte, CorteResponseDTO.class);
    }

    public List<CorteResponseDTO> convertToDTO(List<Corte> cortes) {

        return cortes.stream()
                .map(this::convertToDTO)
                .toList();
    }

}


