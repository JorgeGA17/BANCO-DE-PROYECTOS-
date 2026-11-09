package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.EtiquetaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EtiquetaResponseDTO;
import com.sisgestion_back.sigestion_back.service.EtiquetaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Etiquetas")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class EtiquetaController {

    private final EtiquetaService etiquetaService;

    @GetMapping
    public ResponseEntity<List<EtiquetaResponseDTO>> getAllEtiquetas() {
        List<EtiquetaResponseDTO> etiquetas = etiquetaService.getAllEtiquetas();
        return new ResponseEntity<>(etiquetas, HttpStatus.OK);
    }

    @GetMapping("/{etiquetaPk}")
    public ResponseEntity<EtiquetaResponseDTO> getEtiquetaById(@PathVariable Long etiquetaPk) {
        EtiquetaResponseDTO etiqueta = etiquetaService.getEtiquetaById(etiquetaPk);
        return new ResponseEntity<>(etiqueta, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<EtiquetaResponseDTO> createEtiqueta(@Validated @RequestBody EtiquetaRequestDTO etiquetaDTO) {
        EtiquetaResponseDTO createdEtiqueta = etiquetaService.createEtiqueta(etiquetaDTO);
        return new ResponseEntity<>(createdEtiqueta, HttpStatus.CREATED);
    }

    @PutMapping("/{etiquetaPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<EtiquetaResponseDTO> updateEtiqueta(@PathVariable Long etiquetaPk, @Valid @RequestBody EtiquetaRequestDTO etiquetaDTO) {
        EtiquetaResponseDTO updatedEtiqueta = etiquetaService.updateEtiqueta(etiquetaPk, etiquetaDTO);
        return new ResponseEntity<>(updatedEtiqueta, HttpStatus.OK);
    }

    @DeleteMapping("/{etiquetaPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEtiqueta(@PathVariable Long etiquetaPk) {
        etiquetaService.deleteEtiqueta(etiquetaPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
