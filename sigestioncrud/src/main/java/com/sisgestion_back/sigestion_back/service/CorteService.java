package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.CorteMapper;
import com.sisgestion_back.sigestion_back.model.dto.CorteRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CorteResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Corte;
import com.sisgestion_back.sigestion_back.repository.CorteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor

public class CorteService {

    private CorteRepository corteRepository;
    private CorteMapper corteMapper;

    @Transactional(readOnly = true)
    public List<CorteResponseDTO> getAllCortes() {
        List<Corte> cortes = corteRepository.findAll();
        return corteMapper.convertToDTO(cortes);
    }

    @Transactional(readOnly = true)
    public CorteResponseDTO getCorteById(Long cortePk) {
        Corte corte = corteRepository.findById(cortePk)
                .orElseThrow(()-> new RuntimeException("Corte no encontrada: "+cortePk));
        return corteMapper.convertToDTO(corte);
    }

    @Transactional
    public CorteResponseDTO createCorte (CorteRequestDTO corteRequestDTO) {
        Corte corte = corteMapper.convertToEntity(corteRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        corte.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        corte.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        corte.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        corte.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        corteRepository.save(corte);
        return corteMapper.convertToDTO(corte);
    }

    @Transactional
    public  CorteResponseDTO updateCorte(Long cortePk, CorteRequestDTO corteRequestDTO) {
        Corte corte = corteRepository.findById(cortePk)
                .orElseThrow(()-> new RuntimeException("Corte no encontrada: "+cortePk));
      corte.setXnombre(corteRequestDTO.getXnombre());
      corte.setXnombreCorto(corteRequestDTO.getXnombreCorto());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        corte.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        corte.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        corte.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        corte.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        corte=corteRepository.save(corte);
        return corteMapper.convertToDTO(corte);
    }

    @Transactional
    public void deleteCorte(Long cortePk) {
        corteRepository.deleteById(cortePk);}


}
