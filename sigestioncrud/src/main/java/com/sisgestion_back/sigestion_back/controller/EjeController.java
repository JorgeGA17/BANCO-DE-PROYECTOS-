package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.EjeRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EjeResponseDTO;
import com.sisgestion_back.sigestion_back.service.EjeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ejes")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class EjeController {
    private final EjeService ejeService;

    @GetMapping
    public ResponseEntity<List<EjeResponseDTO>> getAllEjes() {
        List<EjeResponseDTO> ejes = ejeService.getAllEjes();
        return new ResponseEntity<>(ejes, HttpStatus.OK);
    }

    @GetMapping("/{ejePk}")
    public ResponseEntity<EjeResponseDTO> getEjeById(@PathVariable Long ejePk) {
        EjeResponseDTO eje = ejeService.getEjeById(ejePk);
        return new ResponseEntity<>(eje, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EjeResponseDTO> createEje(@Validated @RequestBody EjeRequestDTO ejeDTO) {
        EjeResponseDTO createdEje = ejeService.createEje(ejeDTO);
        return new ResponseEntity<>(createdEje, HttpStatus.CREATED);
    }

    @PutMapping("/{ejePk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EjeResponseDTO> updateEje(@PathVariable Long ejePk, @Valid @RequestBody EjeRequestDTO ejeDTO) {
        EjeResponseDTO updatedEje = ejeService.updateEje(ejePk, ejeDTO);
        return new ResponseEntity<>(updatedEje, HttpStatus.OK);
    }

    @DeleteMapping("/{ejePk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEje(@PathVariable Long ejePk) {
        ejeService.deleteEje(ejePk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
