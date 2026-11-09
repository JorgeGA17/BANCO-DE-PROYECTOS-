package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.JerarquiaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.JerarquiaResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Jerarquia;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component

public class JerarquiaMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {

    }

    public Jerarquia convertToEntity(JerarquiaRequestDTO jerarquiaRequestDTO) {
        return modelMapper.map(jerarquiaRequestDTO, Jerarquia.class);
    }

    public JerarquiaResponseDTO convertToDTO(Jerarquia jerarquia) {
        return modelMapper.map(jerarquia, JerarquiaResponseDTO.class);
    }

    public List<JerarquiaResponseDTO> convertToDTO(List<Jerarquia> jerarquias) {

        return jerarquias.stream()
                .map(this::convertToDTO)
                .toList();
    }


}
