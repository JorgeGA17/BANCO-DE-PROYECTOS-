package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.JerarquiaRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.JerarquiaResponseDTO;
import com.sisgestion_back.sigestion_back.service.JerarquiaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Jerarquias")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")



public class JerarquiaController {

    private final JerarquiaService jerarquiaService;

    @GetMapping
    public ResponseEntity<List<JerarquiaResponseDTO>> getAllJerarquias() {
        List<JerarquiaResponseDTO> jerarquias = jerarquiaService.getAllJerarquias();
        return new ResponseEntity<>(jerarquias, HttpStatus.OK);
    }

    @GetMapping("/{jerarquiaPk}")
    public ResponseEntity<JerarquiaResponseDTO> getJerarquiaById(@PathVariable Long jerarquiaPk) {
        JerarquiaResponseDTO jerarquia= jerarquiaService.getJerarquiaById(jerarquiaPk);
        return new ResponseEntity<>(jerarquia, HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<JerarquiaResponseDTO> createJerarquia(@Validated @RequestBody JerarquiaRequestDTO jerarquiaDTO) {
        JerarquiaResponseDTO createdJerarquia = jerarquiaService.createJerarquia(jerarquiaDTO);
        return new ResponseEntity<>(createdJerarquia, HttpStatus.CREATED);
    }

    @PutMapping("/{jerarquiaPk}")
    @PreAuthorize("hasRole('ADMIN')")
       public ResponseEntity<JerarquiaResponseDTO> updateJerarquia(@PathVariable Long jerarquiaPk, @Valid @RequestBody JerarquiaRequestDTO jerarquiaDTO) {
        JerarquiaResponseDTO uptdateJerarquia = jerarquiaService.updateJerarquia(jerarquiaPk,jerarquiaDTO);
        return new ResponseEntity<>(uptdateJerarquia, HttpStatus.OK);
    }

    @DeleteMapping("/{jerarquiaPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJerarquia(@PathVariable Long jerarquiaPk) {
        jerarquiaService.deleteJerarquia(jerarquiaPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
