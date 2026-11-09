package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.ComisionRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.ComisionResponseDTO;
import com.sisgestion_back.sigestion_back.service.ComisionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Comisiones")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")


public class ComisionController {

    private final ComisionService comisionService;

    @GetMapping
    public ResponseEntity<List<ComisionResponseDTO>> getAllComisiones() {
        List<ComisionResponseDTO> comisiones = comisionService.getAllComisiones();
        return new ResponseEntity<>(comisiones, HttpStatus.OK);
    }

    @GetMapping("/{comisionPk}")
    public ResponseEntity<ComisionResponseDTO> getComisionById(@PathVariable Long comisionPk) {
        ComisionResponseDTO comision = comisionService.getComisionById(comisionPk);
        return new ResponseEntity<>(comision, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<ComisionResponseDTO> createComision(@Validated @RequestBody ComisionRequestDTO comisionDTO) {
        ComisionResponseDTO createdComision = comisionService.createComision(comisionDTO);
        return new ResponseEntity<>(createdComision, HttpStatus.CREATED);
    }

    @PutMapping("/{comisionPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<ComisionResponseDTO> updateComision(@PathVariable Long comisionPk, @Valid @RequestBody ComisionRequestDTO comisionDTO) {
        ComisionResponseDTO updatedComision = comisionService.updateComision(comisionPk, comisionDTO);
        return new ResponseEntity<>(updatedComision, HttpStatus.OK);
    }

    @DeleteMapping("/{comisionPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteComision(@PathVariable Long comisionPk) {
        comisionService.deleteComision(comisionPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
