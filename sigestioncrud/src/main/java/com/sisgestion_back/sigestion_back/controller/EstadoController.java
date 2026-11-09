package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.EstadoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.EstadoResponseDTO;
import com.sisgestion_back.sigestion_back.service.EstadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Estados")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class EstadoController {
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<EstadoResponseDTO>> getAllEstados() {
        List<EstadoResponseDTO> estados = estadoService.getAllEstados();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping("/{estadoPk}")
    public ResponseEntity<EstadoResponseDTO> getEstadoById(@PathVariable Long estadoPk) {
        EstadoResponseDTO estado= estadoService.getEstadoById(estadoPk);
        return new ResponseEntity<>(estado, HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstadoResponseDTO> createEstado(@Validated @RequestBody EstadoRequestDTO estadoDTO) {
        EstadoResponseDTO createdEstado = estadoService.createEstado(estadoDTO);
        return new ResponseEntity<>(createdEstado, HttpStatus.CREATED);
    }

    @PutMapping("/{estadoPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<EstadoResponseDTO> updateEstado(@PathVariable Long estadoPk, @Valid @RequestBody EstadoRequestDTO estadoDTO) {
        EstadoResponseDTO uptdateEstado = estadoService.updateEstado(estadoPk,estadoDTO);
        return new ResponseEntity<>(uptdateEstado, HttpStatus.OK);
    }

    @DeleteMapping("/{estadoPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long estadoPk) {
        estadoService.deleteEstado(estadoPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
