package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.UserProfileDTO;
import com.sisgestion_back.sigestion_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserProfileController {

    private final UserService userService;


    //Actualizar el perfil de usuario
    @PutMapping("/{userPk}")
    public ResponseEntity<UserProfileDTO> updateProfile (@PathVariable Long userPk, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updateProfile =userService.updateUser(userPk,userProfileDTO);
        return new ResponseEntity<>(updateProfile, HttpStatus.OK);

    }

    //obtener perfil de usuario por ID

    @GetMapping("/{userPk}")
    public ResponseEntity<UserProfileDTO> getProfile (@PathVariable Long userPk) {
        UserProfileDTO userProfile = userService.getUserById(userPk);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }



}
