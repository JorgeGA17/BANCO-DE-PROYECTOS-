package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.PeriodoMapper;
import com.sisgestion_back.sigestion_back.model.dto.PeriodoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PeriodoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Periodo;
import com.sisgestion_back.sigestion_back.repository.PeriodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor

public class PeriodoService {

    private PeriodoRepository periodoRepository;
    private PeriodoMapper periodoMapper;

    @Transactional(readOnly = true)
    public List<PeriodoResponseDTO> getAllPeriodos() {
        List<Periodo> periodos = periodoRepository.findAll();
        return periodoMapper.convertToDTO(periodos);
    }

    @Transactional(readOnly = true)
    public PeriodoResponseDTO getPeriodoById(Long periodoPk) {
        Periodo periodo = periodoRepository.findById(periodoPk)
                .orElseThrow(()-> new RuntimeException("Periodo no encontrado: "+periodoPk));
        return periodoMapper.convertToDTO(periodo);
    }

    @Transactional
    public PeriodoResponseDTO createPeriodo (PeriodoRequestDTO periodoRequestDTO) {
        Periodo periodo = periodoMapper.convertToEntity(periodoRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        periodo.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        periodo.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        periodo.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        periodo.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        periodoRepository.save(periodo);
        return periodoMapper.convertToDTO(periodo);
    }

    @Transactional
    public PeriodoResponseDTO updatePeriodo(Long periodoPk, PeriodoRequestDTO periodoRequestDTO) {
        Periodo periodo = periodoRepository.findById(periodoPk)
                .orElseThrow(()-> new RuntimeException("Periodo no encontrado: "+periodoPk));
        periodo.setXnombre(periodoRequestDTO.getXnombre());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        periodo.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        periodo.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        periodo.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        periodo.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        periodo=periodoRepository.save(periodo);
        return periodoMapper.convertToDTO(periodo);
    }

    @Transactional
    public void deletePeriodo(Long periodoPk) {
        periodoRepository.deleteById(periodoPk);
    }
}
