package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.PersonalMapper;
import com.sisgestion_back.sigestion_back.model.dto.PersonalRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PersonalResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Personal;
import com.sisgestion_back.sigestion_back.repository.PersonalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class PersonalService {

    private PersonalRepository personalRepository;
    private PersonalMapper personalMapper;

    @Transactional(readOnly = true)
    public List<PersonalResponseDTO> getAllPersonas() {
        List<Personal> personas = personalRepository.findAll();
        return personalMapper.convertToDTO(personas);
    }

    @Transactional(readOnly = true)
    public PersonalResponseDTO getPersonalById(Long personalPk) {
        Personal personal = personalRepository.findById(personalPk)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado: " + personalPk));
        return personalMapper.convertToDTO(personal);
    }

    @Transactional
    public PersonalResponseDTO createPersonal(PersonalRequestDTO personalRequestDTO) {
        Personal personal = personalMapper.convertToEntity(personalRequestDTO);
        personal.setXnombreCompleto(personal.getXnombre() + " " + personal.getXapellido());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        personal.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        personal.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        personal.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        personal.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        personalRepository.save(personal);
        return personalMapper.convertToDTO(personal);
    }

    @Transactional
    public PersonalResponseDTO updatePersonal(Long personalPk, PersonalRequestDTO personalRequestDTO) {
        Personal personal = personalRepository.findById(personalPk)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado: " + personalPk));

        personal.setXnombre(personalRequestDTO.getXnombre());
        personal.setXapellido(personalRequestDTO.getXapellido());
        personal.setXnombreCompleto(personal.getXnombre() + " " + personal.getXapellido());
        personal.setNnumeroDocumento(personalRequestDTO.getNnumeroDocumento());
        personal.setXtipoDocumento(personalRequestDTO.getXtipoDocumento());


        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        personal.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        personal.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        personal.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        personal.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        personal = personalRepository.save(personal);
        return personalMapper.convertToDTO(personal);
    }

    @Transactional
    public void deletePersonal(Long personalPk) {
        personalRepository.deleteById(personalPk);
    }
}