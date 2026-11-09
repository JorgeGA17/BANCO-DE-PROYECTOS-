package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.EjeMapper;
import com.sisgestion_back.sigestion_back.model.dto.EjeRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EjeResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Eje;
import com.sisgestion_back.sigestion_back.repository.EjeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class EjeService {
        private EjeRepository ejeRepository;
        private EjeMapper ejeMapper;

        @Transactional(readOnly = true)
        public List<EjeResponseDTO> getAllEjes() {
            List<Eje> ejes = ejeRepository.findAll();
            return ejeMapper.convertToDTO(ejes);
        }

        @Transactional(readOnly = true)
        public EjeResponseDTO getEjeById(Long ejePk) {
            Eje eje = ejeRepository.findById(ejePk)
                    .orElseThrow(() -> new RuntimeException("Eje no encontrado: " + ejePk));
            return ejeMapper.convertToDTO(eje);
        }

        @Transactional
        public EjeResponseDTO createEje(EjeRequestDTO ejeRequestDTO) {
            Eje eje = ejeMapper.convertToEntity(ejeRequestDTO);

            // Llenar los campos de auditoría que requieren información del contexto de la solicitud
            eje.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
            eje.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
            eje.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
            eje.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

            ejeRepository.save(eje);
            return ejeMapper.convertToDTO(eje);
        }

        @Transactional
        public EjeResponseDTO updateEje(Long ejePk, EjeRequestDTO ejeRequestDTO) {
            Eje eje = ejeRepository.findById(ejePk)
                    .orElseThrow(() -> new RuntimeException("Eje no encontrado: " + ejePk));
            eje.setXnombre(ejeRequestDTO.getXnombre());
            eje.setXresumen(ejeRequestDTO.getXresumen());

            // Llenar los campos de auditoría que requieren información del contexto de la solicitud
            eje.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
            eje.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
            eje.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
            eje.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

            eje = ejeRepository.save(eje);
            return ejeMapper.convertToDTO(eje);
        }

        @Transactional
        public void deleteEje(Long ejePk) {
            ejeRepository.deleteById(ejePk);
        }
}
