package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.mapper.CargoMapper;
import com.sisgestion_back.sigestion_back.model.dto.CargoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CargoResponseDTO;
import com.sisgestion_back.sigestion_back.model.entity.Cargo;
import com.sisgestion_back.sigestion_back.repository.CargoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@AllArgsConstructor
public class CargoService {
    private CargoRepository cargoRepository;
    private CargoMapper cargoMapper;

    @Transactional(readOnly = true)
    public List<CargoResponseDTO> getAllCargos() {
        List<Cargo> cargos = cargoRepository.findAll();
        return cargoMapper.convertToDTO(cargos);
    }

    @Transactional(readOnly = true)
    public CargoResponseDTO getCargoById(Long cargoPk) {
        Cargo cargo = cargoRepository.findById(cargoPk)
                .orElseThrow(()-> new RuntimeException("Cargo no encontrado: "+cargoPk));
        return cargoMapper.convertToDTO(cargo);
    }

    @Transactional
    public CargoResponseDTO createCargo (CargoRequestDTO cargoRequestDTO) {
        Cargo cargo = cargoMapper.convertToEntity(cargoRequestDTO);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        cargo.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        cargo.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        cargo.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        cargo.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable


        cargoRepository.save(cargo);
        return cargoMapper.convertToDTO(cargo);
    }

    @Transactional
    public CargoResponseDTO updateCargo(Long cargoPk, CargoRequestDTO cargoRequestDTO) {
        Cargo cargo = cargoRepository.findById(cargoPk)
                .orElseThrow(()-> new RuntimeException("Cargo no encontrado: "+cargoPk));
        cargo.setXnombre(cargoRequestDTO.getXnombre());

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        cargo.setCAudUidred("usuario_red_ejemplo_update");
        cargo.setCAudPc(getClientHostname());
        cargo.setNAudIp(getClientIpAddress());
        cargo.setCAudMcaddr(getClientMacAddress());


             cargo=cargoRepository.save(cargo);
        return cargoMapper.convertToDTO(cargo);
    }

    @Transactional
    public void deleteCargo(Long cargoPk) {
        Cargo cargo = cargoRepository.findById(cargoPk)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado: " + cargoPk));

        // IMPLEMENTACIÓN DE ELIMINACIÓN LÓGICA (según el instructivo)
        cargo.setBActivo(false); // Marcar como inactivo
        cargo.setBAud("D"); // Forzar la 'D' para reflejar la eliminación lógica

        // Campos adicionales de auditoría si se desea
        cargo.setCAudUidred("usuario_red_ejemplo_delete");
        cargo.setCAudPc(getClientHostname());
        cargo.setNAudIp(getClientIpAddress());
        cargo.setCAudMcaddr(getClientMacAddress());

        cargoRepository.save(cargo); // Guardar la entidad con el estado inactivo
        // No llamas deleteById(cargoPk) para eliminación lógica
    }
}
