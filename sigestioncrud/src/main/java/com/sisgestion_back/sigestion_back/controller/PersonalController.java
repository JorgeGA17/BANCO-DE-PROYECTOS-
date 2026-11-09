package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.PersonalRequestDTO;
import com.sisgestion_back.sigestion_back.model.dto.PersonalResponseDTO;
import com.sisgestion_back.sigestion_back.service.PersonalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Personas")
@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:4200/")

public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<PersonalResponseDTO>> getAllPersonas() {
        List<PersonalResponseDTO> personas = personalService.getAllPersonas();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @GetMapping("/{personalPk}")
    public ResponseEntity<PersonalResponseDTO> getPersonalById(@PathVariable Long personalPk) {
        PersonalResponseDTO personal = personalService.getPersonalById(personalPk);
        return new ResponseEntity<>(personal, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<PersonalResponseDTO> createPersonal(@Validated @RequestBody PersonalRequestDTO personalDTO) {
        PersonalResponseDTO createdPersonal = personalService.createPersonal(personalDTO);
        return new ResponseEntity<>(createdPersonal, HttpStatus.CREATED);
    }

    @PutMapping("/{personalPk}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETARIO')")
    public ResponseEntity<PersonalResponseDTO> updatePersonal(@PathVariable Long personalPk, @Valid @RequestBody PersonalRequestDTO personalDTO) {
        PersonalResponseDTO updatedPersonal = personalService.updatePersonal(personalPk, personalDTO);
        return new ResponseEntity<>(updatedPersonal, HttpStatus.OK);
    }

    @DeleteMapping("/{personalPk}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePersonal(@PathVariable Long personalPk) {
        personalService.deletePersonal(personalPk);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}