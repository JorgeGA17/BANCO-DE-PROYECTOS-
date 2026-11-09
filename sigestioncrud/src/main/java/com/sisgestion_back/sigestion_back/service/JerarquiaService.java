package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.JerarquiaMapper;
import com.sisgestion_back.sigestion_back.model.dto.JerarquiaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.JerarquiaResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Jerarquia;

import com.sisgestion_back.sigestion_back.repository.JerarquiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class JerarquiaService {

    private JerarquiaRepository jerarquiaRepository;
    private JerarquiaMapper jerarquiaMapper;

    @Transactional(readOnly = true)
    public List<JerarquiaResponseDTO> getAllJerarquias() {
        List<Jerarquia> jerarquias = jerarquiaRepository.findAll();
        return jerarquiaMapper.convertToDTO(jerarquias);
    }

    @Transactional(readOnly = true)
    public JerarquiaResponseDTO getJerarquiaById(Long jerarquiaPk) {
        Jerarquia jerarquia = jerarquiaRepository.findById(jerarquiaPk)
                .orElseThrow(()-> new RuntimeException("Jerarquia no encontrada: "+jerarquiaPk));
        return jerarquiaMapper.convertToDTO(jerarquia);
    }

    @Transactional
    public JerarquiaResponseDTO createJerarquia (JerarquiaRequestDTO jerarquiaRequestDTO) {
        Jerarquia jerarquia = jerarquiaMapper.convertToEntity(jerarquiaRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        jerarquia.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        jerarquia.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        jerarquia.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        jerarquia.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        jerarquiaRepository.save(jerarquia);
        return jerarquiaMapper.convertToDTO(jerarquia);
    }

    @Transactional
    public  JerarquiaResponseDTO updateJerarquia (Long jerarquiaPk, JerarquiaRequestDTO jerarquiaRequestDTO) {
        Jerarquia jerarquia = jerarquiaRepository.findById(jerarquiaPk)
                .orElseThrow(()-> new RuntimeException("Jerarquia no encontrada: "+ jerarquiaPk));
        jerarquia.setXnombre(jerarquiaRequestDTO.getXnombre());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        jerarquia.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        jerarquia.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        jerarquia.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        jerarquia.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        jerarquia=jerarquiaRepository.save(jerarquia);
        return jerarquiaMapper.convertToDTO(jerarquia);
    }

    @Transactional
    public void deleteJerarquia(Long jerarquiaPk) {
        jerarquiaRepository.deleteById(jerarquiaPk);}

}
