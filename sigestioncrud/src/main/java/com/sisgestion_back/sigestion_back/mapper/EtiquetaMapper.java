package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.EtiquetaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EtiquetaResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Etiqueta;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component

public class EtiquetaMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {

    }

    public Etiqueta convertToEntity(EtiquetaRequestDTO etiquetaRequestDTO) {
        return modelMapper.map(etiquetaRequestDTO, Etiqueta.class);
    }

    public EtiquetaResponseDTO convertToDTO(Etiqueta etiqueta) {
        return modelMapper.map(etiqueta, EtiquetaResponseDTO.class);
    }

    public List<EtiquetaResponseDTO> convertToDTO(List<Etiqueta> etiquetas) {
        return etiquetas.stream()
                .map(this::convertToDTO)
                .toList();
    }


}
