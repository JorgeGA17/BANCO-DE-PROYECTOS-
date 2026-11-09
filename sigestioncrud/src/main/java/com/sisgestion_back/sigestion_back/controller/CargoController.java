package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.CargoRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.CargoResponseDTO;
import com.sisgestion_back.sigestion_back.service.CargoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cargos")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> getAllCargos() {
        List<CargoResponseDTO> cargos = cargoService.getAllCargos();
        return new ResponseEntity<>(cargos, HttpStatus.OK);
    }

    @GetMapping("/{cargoPk}")
    public ResponseEntity<CargoResponseDTO> getCargoById(@PathVariable Long cargoPk) {
        CargoResponseDTO cargo = cargoService.getCargoById(cargoPk);
        return new ResponseEntity<>(cargo, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoResponseDTO> createCargo(@Validated @RequestBody CargoRequestDTO cargoDTO) {
        CargoResponseDTO createdCargo = cargoService.createCargo(cargoDTO);
        return new ResponseEntity<>(createdCargo, HttpStatus.CREATED);
    }

    @PutMapping("/{cargoPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoResponseDTO> updateCargo(@PathVariable Long cargoPk, @Valid @RequestBody CargoRequestDTO cargoDTO) {
        CargoResponseDTO updatedCargo = cargoService.updateCargo(cargoPk, cargoDTO);
        return new ResponseEntity<>(updatedCargo, HttpStatus.OK);
    }

    @DeleteMapping("/{cargoPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long cargoPk) {
        cargoService.deleteCargo(cargoPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
