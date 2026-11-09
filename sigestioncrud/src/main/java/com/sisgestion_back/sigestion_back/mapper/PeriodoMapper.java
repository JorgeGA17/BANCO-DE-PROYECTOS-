package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.PeriodoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PeriodoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Periodo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PeriodoMapper {

    private final ModelMapper modelMapper;

    public Periodo convertToEntity(PeriodoRequestDTO periodoRequestDTO) {
        return modelMapper.map(periodoRequestDTO, Periodo.class);
    }

    public PeriodoResponseDTO convertToDTO(Periodo periodo) {
        return modelMapper.map(periodo, PeriodoResponseDTO.class);
    }

    public List<PeriodoResponseDTO> convertToDTO(List<Periodo> periodos) {

        return periodos.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
