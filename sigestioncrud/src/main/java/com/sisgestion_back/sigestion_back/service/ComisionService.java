package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.ComisionMapper;
import com.sisgestion_back.sigestion_back.model.dto.ComisionRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.ComisionResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Comision;
import com.sisgestion_back.sigestion_back.model.entity.Corte;
import com.sisgestion_back.sigestion_back.model.entity.Periodo;
import com.sisgestion_back.sigestion_back.repository.ComisionRepository;
import com.sisgestion_back.sigestion_back.repository.CorteRepository;
import com.sisgestion_back.sigestion_back.repository.PeriodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class ComisionService {

    private ComisionRepository comisionRepository;
    private ComisionMapper comisionMapper;
    private final CorteRepository corteRepository;
    private final PeriodoRepository periodoRepository;

    @Transactional(readOnly = true)
    public List<ComisionResponseDTO> getAllComisiones() {
        List<Comision> comisiones = comisionRepository.findAll();
        return comisionMapper.convertToDTO(comisiones);
    }

    @Transactional(readOnly = true)
    public ComisionResponseDTO getComisionById(Long comisionPk) {
        Comision comision = comisionRepository.findById(comisionPk)
                .orElseThrow(()-> new RuntimeException("Comision no encontrada"+comisionPk));
        return comisionMapper.convertToDTO(comision);
    }

    @Transactional
    public ComisionResponseDTO createComision (ComisionRequestDTO comisionRequestDTO) {
        Comision comision = comisionMapper.convertToEntity(comisionRequestDTO);


        //Obtener y asignar entidades ManyToOne (Corte y Periodo)
        Corte corte = corteRepository.findById(comisionRequestDTO.getCorteId())
                .orElseThrow(() -> new RuntimeException("Corte no encontrado con ID: " + comisionRequestDTO.getCorteId()));
        Periodo periodo = periodoRepository.findById(comisionRequestDTO.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado con ID: " + comisionRequestDTO.getPeriodoId()));
        comision.setCortefk(corte);
        comision.setPeriodofk(periodo);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        comision.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        comision.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        comision.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        comision.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        comisionRepository.save(comision);
        return comisionMapper.convertToDTO(comision);
    }

    @Transactional
    public ComisionResponseDTO updateComision(Long comisionPk, ComisionRequestDTO comisionRequestDTO) {
        Comision comision = comisionRepository.findById(comisionPk)
                .orElseThrow(()-> new RuntimeException("Comision no encontrada"+comisionPk));
        comision.setXdescripcion(comisionRequestDTO.getXdescripcion());
        //Obtener y asignar entidades ManyToOne (Corte y Periodo)
        Corte corte = corteRepository.findById(comisionRequestDTO.getCorteId())
                .orElseThrow(() -> new RuntimeException("Corte no encontrado con ID: " + comisionRequestDTO.getCorteId()));
        Periodo periodo = periodoRepository.findById(comisionRequestDTO.getPeriodoId())
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado con ID: " + comisionRequestDTO.getPeriodoId()));
        comision.setCortefk(corte);
        comision.setPeriodofk(periodo);


        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        comision.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        comision.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        comision.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        comision.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        comision=comisionRepository.save(comision);
        return comisionMapper.convertToDTO(comision);
    }

    @Transactional
    public void deleteComision(Long comisionPk) {
        comisionRepository.deleteById(comisionPk);
    }
}
