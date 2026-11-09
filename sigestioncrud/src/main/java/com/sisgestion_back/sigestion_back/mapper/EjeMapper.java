package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.EjeRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EjeResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Eje;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class EjeMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {

    }

    public Eje convertToEntity(EjeRequestDTO ejeRequestDTO) {
        return modelMapper.map(ejeRequestDTO, Eje.class);
    }

    public EjeResponseDTO convertToDTO(Eje eje) {
        return modelMapper.map(eje, EjeResponseDTO.class);
    }

    public List<EjeResponseDTO> convertToDTO(List<Eje> ejes) {

        return ejes.stream()
                .map(this::convertToDTO)
                .toList();
    }
}