package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.MiembroRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.MiembroResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Miembro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import java.util.List;

@AllArgsConstructor
@Component
public class MiembroMapper {

    private final ModelMapper modelMapper;

    public Miembro convertToEntity(MiembroRequestDTO miembroRequestDTO) {
        return modelMapper.map(miembroRequestDTO, Miembro.class);
    }

    public MiembroResponseDTO convertToDTO(Miembro miembro) {
        MiembroResponseDTO miembroResponseDTO = modelMapper.map(miembro, MiembroResponseDTO.class);

        // Mapeo para relaciones ManyToOne (Comision)
        if (miembro.getComisionfk() != null) {
            miembroResponseDTO.setComisionId(miembro.getComisionfk().getComisionPk());
            miembroResponseDTO.setComisionNombreCorte(miembro.getComisionfk().getCortefk().getXnombreCorto());
        } else {
            miembroResponseDTO.setComisionId(null);
            miembroResponseDTO.setComisionNombreCorte(null);
        }

        if (miembro.getPersonalfk() != null) {
            miembroResponseDTO.setPersonalId(miembro.getPersonalfk().getPersonalPk());
            miembroResponseDTO.setPersonalNombre(miembro.getPersonalfk().getXnombreCompleto());
            miembroResponseDTO.setPersonalCorreo(miembro.getPersonalfk().getXcorreoInstitucional());
        } else {
            miembroResponseDTO.setPersonalId(null);
            miembroResponseDTO.setPersonalNombre(null);
            miembroResponseDTO.setPersonalCorreo(null);
        }

        if (miembro.getCargofk() != null) {
            miembroResponseDTO.setCargoId(miembro.getCargofk().getCargoPk());
            miembroResponseDTO.setCargoNombre(miembro.getCargofk().getXnombre());
        } else {
            miembroResponseDTO.setCargoId(null);
            miembroResponseDTO.setCargoNombre(null);
        }

        return miembroResponseDTO;

    }

    public List<MiembroResponseDTO> convertToDTO(List<Miembro> miembros) {
        return miembros.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
