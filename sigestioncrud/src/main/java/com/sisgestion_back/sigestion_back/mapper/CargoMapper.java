package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.CargoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CargoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Cargo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CargoMapper {

    private final ModelMapper modelMapper;

    public Cargo convertToEntity(CargoRequestDTO cargoRequestDTO) {
        return modelMapper.map(cargoRequestDTO, Cargo.class);
    }

    public CargoResponseDTO convertToDTO(Cargo cargo) {
        return modelMapper.map(cargo, CargoResponseDTO.class);
    }

    public List<CargoResponseDTO> convertToDTO(List<Cargo> cargos) {
        return cargos.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
