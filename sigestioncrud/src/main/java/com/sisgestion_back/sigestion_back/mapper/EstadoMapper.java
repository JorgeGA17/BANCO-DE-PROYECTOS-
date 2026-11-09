package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.EstadoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EstadoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component

public class EstadoMapper {

    private final ModelMapper modelMapper;

    public Estado convertToEntity(EstadoRequestDTO estadoRequestDTO) {
        return modelMapper.map(estadoRequestDTO, Estado.class);
    }

    public EstadoResponseDTO convertToDTO(Estado estado) {
        return modelMapper.map(estado, EstadoResponseDTO.class);
    }

    public List<EstadoResponseDTO> convertToDTO(List<Estado> estados) {

        return estados.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
