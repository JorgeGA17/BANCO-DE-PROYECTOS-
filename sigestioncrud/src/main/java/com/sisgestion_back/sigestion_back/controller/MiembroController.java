package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.MiembroRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.MiembroResponseDTO;
import com.sisgestion_back.sigestion_back.service.MiembroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Miembros")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class MiembroController {

    private final MiembroService miembroService;

    @GetMapping
    public ResponseEntity<List<MiembroResponseDTO>> getAllMiembros() {
        List<MiembroResponseDTO> miembros = miembroService.getAllMiembros();
        return new ResponseEntity<>(miembros, HttpStatus.OK);
    }

    @GetMapping("/{miembroPk}")
    public ResponseEntity<MiembroResponseDTO> getMiembroById(@PathVariable Long miembroPk) {
        MiembroResponseDTO miembro = miembroService.getMiembroById(miembroPk);
        return new ResponseEntity<>(miembro, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<MiembroResponseDTO> createMiembro(@Validated @RequestBody MiembroRequestDTO miembroDTO) {
        MiembroResponseDTO createdMiembro = miembroService.createMiembro(miembroDTO);
        return new ResponseEntity<>(createdMiembro, HttpStatus.CREATED);
    }

    @PutMapping("/{miembroPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<MiembroResponseDTO> updateMiembro(@PathVariable Long miembroPk, @Valid @RequestBody MiembroRequestDTO miembroDTO) {
        MiembroResponseDTO updatedMiembro = miembroService.updateMiembro(miembroPk, miembroDTO);
        return new ResponseEntity<>(updatedMiembro, HttpStatus.OK);
    }

    @DeleteMapping("/{miembroPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Long miembroPk) {
        miembroService.deleteMiembro(miembroPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
