package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.EspecialidadRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EspecialidadResponseDTO;
import com.sisgestion_back.sigestion_back.service.EspecialidadService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/Especialidades")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> getAllEspecialidades() {
        List<EspecialidadResponseDTO> especialidades = especialidadService.getAllEspecialidades();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    @GetMapping("/{especialidadPk}")
    public ResponseEntity<EspecialidadResponseDTO> getEspecialidadById(@PathVariable Long especialidadPk) {
        EspecialidadResponseDTO especialidad= especialidadService.getEspecialidadById(especialidadPk);
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadResponseDTO> createEspecialidad(@Validated @RequestBody EspecialidadRequestDTO especialidadDTO) {
        EspecialidadResponseDTO createdEspecialidad = especialidadService.createEspecialidad(especialidadDTO);
        return new ResponseEntity<>(createdEspecialidad, HttpStatus.CREATED);
    }

    @PutMapping("/{especialidadPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadResponseDTO> updateEspecialidad(@PathVariable Long especialidadPk, @Valid @RequestBody EspecialidadRequestDTO especialidadDTO) {
        EspecialidadResponseDTO uptdateEspecialidad = especialidadService.updateEspecialidad(especialidadPk,especialidadDTO);
        return new ResponseEntity<>(uptdateEspecialidad, HttpStatus.OK);
    }

    @DeleteMapping("/{especialidadPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long especialidadPk) {
        especialidadService.deleteEspecialidad(especialidadPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

