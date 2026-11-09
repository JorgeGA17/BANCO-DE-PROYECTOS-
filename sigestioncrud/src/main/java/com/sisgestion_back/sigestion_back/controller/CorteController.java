package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.CorteRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CorteResponseDTO;
import com.sisgestion_back.sigestion_back.service.CorteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cortes")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")


public class CorteController {
    private final CorteService corteService;

    @GetMapping
    public ResponseEntity<List<CorteResponseDTO>> getAllCortes() {
        List<CorteResponseDTO> cortes = corteService.getAllCortes();
        return new ResponseEntity<>(cortes, HttpStatus.OK);
   }

    @GetMapping("/{cortePk}")

    public ResponseEntity<CorteResponseDTO> getCorteById(@PathVariable Long cortePk) {
        CorteResponseDTO corte= corteService.getCorteById(cortePk);
        return new ResponseEntity<>(corte, HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CorteResponseDTO> createCorte(@Validated @RequestBody CorteRequestDTO corteDTO) {
        CorteResponseDTO createdCorte = corteService.createCorte(corteDTO);
        return new ResponseEntity<>(createdCorte, HttpStatus.CREATED);
    }

    @PutMapping("/{cortePk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CorteResponseDTO> updateCorte(@PathVariable Long cortePk, @Valid @RequestBody CorteRequestDTO corteDTO) {
        CorteResponseDTO uptdateCorte = corteService.updateCorte(cortePk,corteDTO);
        return new ResponseEntity<>(uptdateCorte, HttpStatus.OK);
    }

    @DeleteMapping("/{cortePk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCorte(@PathVariable Long cortePk) {
        corteService.deleteCorte(cortePk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
