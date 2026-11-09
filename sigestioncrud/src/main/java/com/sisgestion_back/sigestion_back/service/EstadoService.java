package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.EstadoMapper;
import com.sisgestion_back.sigestion_back.model.dto.EstadoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EstadoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Estado;
import com.sisgestion_back.sigestion_back.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class EstadoService {

    private EstadoRepository estadoRepository;
    private EstadoMapper estadoMapper;

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> getAllEstados() {
        List<Estado> estados = estadoRepository.findAll();
        return estadoMapper.convertToDTO(estados);
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO getEstadoById(Long estadoPk) {
        Estado estado = estadoRepository.findById(estadoPk)
                .orElseThrow(()-> new RuntimeException("Estado no encontrado: "+estadoPk));
        return estadoMapper.convertToDTO(estado);
    }

    @Transactional
    public EstadoResponseDTO createEstado (EstadoRequestDTO estadoRequestDTO) {
        Estado estado = estadoMapper.convertToEntity(estadoRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        estado.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        estado.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        estado.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        estado.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        estadoRepository.save(estado);
        return estadoMapper.convertToDTO(estado);
    }

    @Transactional
    public  EstadoResponseDTO updateEstado (Long estadoPk, EstadoRequestDTO estadoRequestDTO) {
        Estado estado = estadoRepository.findById(estadoPk)
                .orElseThrow(()-> new RuntimeException("Estado no encontrada: "+estadoPk));
        estado.setXnombre(estadoRequestDTO.getXnombre());
        estado.setXresumen(estadoRequestDTO.getXresumen());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        estado.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        estado.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        estado.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        estado.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        estado=estadoRepository.save(estado);
        return estadoMapper.convertToDTO(estado);
    }

    @Transactional
    public void deleteEstado(Long estadoPk) {
        estadoRepository.deleteById(estadoPk);}
}
