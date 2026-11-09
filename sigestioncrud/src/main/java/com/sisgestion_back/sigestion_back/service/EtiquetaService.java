package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.EtiquetaMapper;
import com.sisgestion_back.sigestion_back.model.dto.EtiquetaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EtiquetaResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Etiqueta;
import com.sisgestion_back.sigestion_back.repository.EtiquetaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class EtiquetaService {

    private EtiquetaRepository etiquetaRepository;
    private EtiquetaMapper etiquetaMapper;

    @Transactional(readOnly = true)
    public List<EtiquetaResponseDTO> getAllEtiquetas() {
        List<Etiqueta> etiquetas = etiquetaRepository.findAll();
        return etiquetaMapper.convertToDTO(etiquetas);
    }

    @Transactional(readOnly = true)
    public EtiquetaResponseDTO getEtiquetaById(Long etiquetaPk) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaPk)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada: " + etiquetaPk));
        return etiquetaMapper.convertToDTO(etiqueta);
    }

    @Transactional
    public EtiquetaResponseDTO createEtiqueta(EtiquetaRequestDTO etiquetaRequestDTO) {
        Etiqueta etiqueta = etiquetaMapper.convertToEntity(etiquetaRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        etiqueta.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        etiqueta.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        etiqueta.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        etiqueta.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        etiquetaRepository.save(etiqueta);
        return etiquetaMapper.convertToDTO(etiqueta);
    }

    @Transactional
    public EtiquetaResponseDTO updateEtiqueta(Long etiquetaPk, EtiquetaRequestDTO etiquetaRequestDTO) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaPk)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada: " + etiquetaPk));
        etiqueta.setXnombre(etiquetaRequestDTO.getXnombre());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        etiqueta.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        etiqueta.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        etiqueta.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        etiqueta.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        etiqueta = etiquetaRepository.save(etiqueta);
        return etiquetaMapper.convertToDTO(etiqueta);
    }

    @Transactional
    public void deleteEtiqueta(Long etiquetaPk) {
        etiquetaRepository.deleteById(etiquetaPk);
    }
}
