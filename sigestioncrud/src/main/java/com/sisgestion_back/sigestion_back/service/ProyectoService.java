package com.sisgestion_back.sigestion_back.service;


import com.sisgestion_back.sigestion_back.mapper.ProyectoMapper;
import com.sisgestion_back.sigestion_back.model.dto.*;
import com.sisgestion_back.sigestion_back.model.entity.Corte;
import com.sisgestion_back.sigestion_back.model.entity.Especialidad;
import com.sisgestion_back.sigestion_back.model.entity.Estado;
import com.sisgestion_back.sigestion_back.model.entity.Proyecto;
import com.sisgestion_back.sigestion_back.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;


@Service
@AllArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoMapper proyectoMapper;
    private final CorteRepository corteRepository;
    private final EstadoRepository estadoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final JerarquiaRepository jerarquiaRepository;
    private final EjeRepository ejeRepository;
    private final EtiquetaRepository etiquetaRepository;
    private final PersonalRepository personalRepository;


    private <T> List<T> findAndGetEntitiesByIds(List<Long> ids, JpaRepository<T, Long> repository, String entityName) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> entities = repository.findAllById(ids);
        if (entities.size() != ids.size()) {
            throw new RuntimeException("Error: No todas las " + entityName + " fueron encontradas con los IDs proporcionados.");
        }
        return entities;
    }

    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> getAllProyectos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return proyectoMapper.convertToDTO(proyectos);
    }

    @Transactional(readOnly = true)
    public ProyectoResponseDTO getProyectoById(Long proyectopk) {
        Proyecto proyecto = proyectoRepository.findById(proyectopk)
                .orElseThrow(()-> new RuntimeException("Proyecto no encontrado: " + proyectopk));
        return proyectoMapper.convertToDTO(proyecto);
    }

    @Transactional
    public ProyectoResponseDTO createProyecto(ProyectoRequestDTO proyectoRequestDTO) {
        // 1. Mapear DTO a entidad Proyecto (campos básicos)
        Proyecto proyecto = proyectoMapper.convertToEntity(proyectoRequestDTO);

        // 2. Asignar la fecha de registro


        // 3. Obtener y asignar entidades ManyToOne (Corte y Estado)
        Corte corte = corteRepository.findById(proyectoRequestDTO.getCorteId())
                .orElseThrow(() -> new RuntimeException("Corte no encontrado con ID: " + proyectoRequestDTO.getCorteId()));
        Estado estado = estadoRepository.findById(proyectoRequestDTO.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + proyectoRequestDTO.getEstadoId()));
        proyecto.setCortefk(corte);
        proyecto.setEstadofk(estado);

        // 4. Obtener y asignar colecciones ManyToMany usando el método auxiliar
        proyecto.setEspecialidades(findAndGetEntitiesByIds(proyectoRequestDTO.getEspecialidadIds(), especialidadRepository, "Especialidad"));
        proyecto.setJerarquias(findAndGetEntitiesByIds(proyectoRequestDTO.getJerarquiaIds(), jerarquiaRepository, "Jerarquía"));
        proyecto.setEjes(findAndGetEntitiesByIds(proyectoRequestDTO.getEjeIds(), ejeRepository, "Eje"));
        proyecto.setEtiquetas(findAndGetEntitiesByIds(proyectoRequestDTO.getEtiquetaIds(), etiquetaRepository, "Etiqueta"));
        proyecto.setPersonal(findAndGetEntitiesByIds(proyectoRequestDTO.getPersonalIds(), personalRepository, "Personal"));

        // 5. Calcular y asignar npeso
        Integer npeso = proyecto.getXimpacto() + proyecto.getXinnovacion() + proyecto.getXsostenibilidad() + proyecto.getXreplicabilidad();
        proyecto.setNpeso(npeso);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        proyecto.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        proyecto.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        proyecto.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        proyecto.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        // 6. Guardar el proyecto y convertir a DTO de respuesta
        proyectoRepository.save(proyecto);
        return proyectoMapper.convertToDTO(proyecto);
    }

    @Transactional
    public ProyectoResponseDTO updateProyecto(Long proyectopk, ProyectoRequestDTO proyectoRequestDTO) {
        // 1. Encontrar el proyecto existente
        Proyecto proyecto = proyectoRepository.findById(proyectopk)
                .orElseThrow(()-> new RuntimeException("Proyecto no encontrado: " + " " +proyectopk));

        // 2. Actualizar campos directos del DTO (ModelMapper podría ayudar aquí, pero manual es explícito)
        proyecto.setXnombreProyecto(proyectoRequestDTO.getXnombreProyecto());
        proyecto.setXproblematica(proyectoRequestDTO.getXproblematica());
        proyecto.setXresumen(proyectoRequestDTO.getXresumen());
        proyecto.setXobjetivoGeneral(proyectoRequestDTO.getXobjetivoGeneral());
        proyecto.setXinnovacion(proyectoRequestDTO.getXinnovacion());
        proyecto.setXimpacto(proyectoRequestDTO.getXimpacto());
        proyecto.setXsostenibilidad(proyectoRequestDTO.getXsostenibilidad());
        proyecto.setXreplicabilidad(proyectoRequestDTO.getXreplicabilidad());
        proyecto.setXconceptoEval(proyectoRequestDTO.getXconceptoEval());
        proyecto.setXdescripEval(proyectoRequestDTO.getXdescripEval());


        // 3. Actualizar relaciones ManyToOne si se proporcionan nuevos IDs (similar a create)
        Corte corte = corteRepository.findById(proyectoRequestDTO.getCorteId())
                .orElseThrow(() -> new RuntimeException("Corte no encontrado con ID: " + proyectoRequestDTO.getCorteId()));
        Estado estado = estadoRepository.findById(proyectoRequestDTO.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + proyectoRequestDTO.getEstadoId()));
        proyecto.setCortefk(corte);
        proyecto.setEstadofk(estado);


        // 4. Actualizar colecciones ManyToMany (se sobrescriben con las nuevas IDs)
        proyecto.setEspecialidades(findAndGetEntitiesByIds(proyectoRequestDTO.getEspecialidadIds(), especialidadRepository, "Especialidad"));
        proyecto.setJerarquias(findAndGetEntitiesByIds(proyectoRequestDTO.getJerarquiaIds(), jerarquiaRepository, "Jerarquía"));
        proyecto.setEjes(findAndGetEntitiesByIds(proyectoRequestDTO.getEjeIds(), ejeRepository, "Eje"));
        proyecto.setEtiquetas(findAndGetEntitiesByIds(proyectoRequestDTO.getEtiquetaIds(), etiquetaRepository, "Etiqueta"));
        proyecto.setPersonal(findAndGetEntitiesByIds(proyectoRequestDTO.getPersonalIds(), personalRepository, "Personal"));

        // 5. Recalcular npeso
        Integer npeso = proyecto.getXimpacto() + proyecto.getXinnovacion() + proyecto.getXsostenibilidad() + proyecto.getXreplicabilidad();
        proyecto.setNpeso(npeso);

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        proyecto.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        proyecto.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        proyecto.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        proyecto.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        // 6. Guardar los cambios y convertir a DTO de respuesta
        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.convertToDTO(proyecto);
    }

    @Transactional
    public void deleteProyecto(Long proyectopk) {
        proyectoRepository.deleteById(proyectopk);
    }
}