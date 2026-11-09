package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.PersonalRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PersonalResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Personal;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PersonalMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {

    }

    public Personal convertToEntity(PersonalRequestDTO personalRequestDTO) {
        return modelMapper.map(personalRequestDTO, Personal.class);
    }

    public PersonalResponseDTO convertToDTO(Personal personal) {
        return modelMapper.map(personal, PersonalResponseDTO.class);
    }

    public List<PersonalResponseDTO> convertToDTO(List<Personal> personas) {

        return personas.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
