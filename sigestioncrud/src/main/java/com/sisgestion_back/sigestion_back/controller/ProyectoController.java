package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.ProyectoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.ProyectoResponseDTO;
import com.sisgestion_back.sigestion_back.service.ProyectoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Proyectos")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class ProyectoController {

    private final ProyectoService proyectoService;


    @GetMapping
    public ResponseEntity<List<ProyectoResponseDTO>> getAllProyectos() {
        List<ProyectoResponseDTO> proyectos = proyectoService.getAllProyectos();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
     }


    @GetMapping("/{proyectopk}")
    public ResponseEntity<ProyectoResponseDTO> getProyectoById(@PathVariable Long proyectopk) {
        ProyectoResponseDTO proyecto= proyectoService.getProyectoById(proyectopk);
        return new ResponseEntity<>(proyecto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<ProyectoResponseDTO> createProyecto(@Validated @RequestBody ProyectoRequestDTO proyectoDTO) {
        ProyectoResponseDTO createdProyecto = proyectoService.createProyecto(proyectoDTO);
        return new ResponseEntity<>(createdProyecto, HttpStatus.CREATED);
    }

    @PutMapping("/{proyectopk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<ProyectoResponseDTO> updateProyecto(@PathVariable Long proyectopk, @Valid @RequestBody ProyectoRequestDTO proyectoDTO) {
        ProyectoResponseDTO uptdateProyecto = proyectoService.updateProyecto(proyectopk,proyectoDTO);
        return new ResponseEntity<>(uptdateProyecto, HttpStatus.OK);
    }

    @DeleteMapping("/{proyectopk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long proyectopk) {
        proyectoService.deleteProyecto(proyectopk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
