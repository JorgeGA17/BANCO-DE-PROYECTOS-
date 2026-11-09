package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.ComisionRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.ComisionResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Comision;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ComisionMapper {

    private final ModelMapper modelMapper;

    public Comision convertToEntity(ComisionRequestDTO comisionRequestDTO) {
        return modelMapper.map(comisionRequestDTO, Comision.class);
    }

    public ComisionResponseDTO convertToDTO(Comision comision) {
      ComisionResponseDTO comisionResponseDTO = modelMapper.map(comision, ComisionResponseDTO.class);

        // Mapeo para relaciones ManyToOne (Corte y Estado)
        if (comision.getCortefk() != null) {
            comisionResponseDTO.setCorteId(comision.getCortefk().getCortePk());
            comisionResponseDTO.setNombreCorte(comision.getCortefk().getXnombre());
        } else {
            comisionResponseDTO.setCorteId(null);
            comisionResponseDTO.setNombreCorte(null);
        }

        // Mapeo para relaciones ManyToOne (Corte y Estado)
        if (comision.getPeriodofk() != null) {
            comisionResponseDTO.setPeriodoId(comision.getPeriodofk().getPeriodoPk());
            comisionResponseDTO.setNombrePeriodo(comision.getPeriodofk().getXnombre());
        } else {
            comisionResponseDTO.setPeriodoId(null);
            comisionResponseDTO.setNombrePeriodo(null);
        }

        return comisionResponseDTO;
    }

    public List<ComisionResponseDTO> convertToDTO(List<Comision> comisiones) {

        return comisiones.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
