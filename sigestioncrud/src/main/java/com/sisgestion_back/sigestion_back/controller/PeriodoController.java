package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.PeriodoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PeriodoResponseDTO;
import com.sisgestion_back.sigestion_back.service.PeriodoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Periodos")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class PeriodoController {

    private final PeriodoService periodoService;

    @GetMapping
    public ResponseEntity<List<PeriodoResponseDTO>> getAllPeriodos() {
        List<PeriodoResponseDTO> periodos = periodoService.getAllPeriodos();
        return new ResponseEntity<>(periodos, HttpStatus.OK);
    }

    @GetMapping("/{periodoPk}")
    public ResponseEntity<PeriodoResponseDTO> getPeriodoById(@PathVariable Long periodoPk) {
        PeriodoResponseDTO periodo = periodoService.getPeriodoById(periodoPk);
        return new ResponseEntity<>(periodo, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<PeriodoResponseDTO> createPeriodo(@Validated @RequestBody PeriodoRequestDTO periodoDTO) {
        PeriodoResponseDTO createdPeriodo = periodoService.createPeriodo(periodoDTO);
        return new ResponseEntity<>(createdPeriodo, HttpStatus.CREATED);
    }

    @PutMapping("/{periodoPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<PeriodoResponseDTO> updatePeriodo(@PathVariable Long periodoPk, @Valid @RequestBody PeriodoRequestDTO periodoDTO) {
        PeriodoResponseDTO updatedPeriodo = periodoService.updatePeriodo(periodoPk, periodoDTO);
        return new ResponseEntity<>(updatedPeriodo, HttpStatus.OK);
    }

    @DeleteMapping("/{periodoPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long periodoPk) {
        periodoService.deletePeriodo(periodoPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
