package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.model.dto.AuthResponseDTO;
import com.sisgestion_back.sigestion_back.model.dto.ForgotPasswordDTO;
import com.sisgestion_back.sigestion_back.model.dto.LoginDTO;
import com.sisgestion_back.sigestion_back.model.dto.ResetPasswordDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserProfileDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserRegistrationDTO;
import com.sisgestion_back.sigestion_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //Registrar ADMIN
    @RequestMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileDTO> registerAdmin (@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerAdmin(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    //Registrar Secretario
    @RequestMapping("/register/secretario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileDTO> registerSecretario (@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerSecretario(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    //Registrar General
    @RequestMapping("/register/general")
    public ResponseEntity<UserProfileDTO> registerGeneral (@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerGeneral(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        String token = userService.sendPasswordRecoveryEmail(forgotPasswordDTO.getXcorreoInstitucional());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Token de recuperación generado exitosamente.");
        response.put("token", token);
        response.put("resetUrl", "http://localhost:4200/reset-password?token=" + token);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.resetPassword(resetPasswordDTO.getToken(), resetPasswordDTO.getNewPassword());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contraseña actualizada exitosamente.");
        return ResponseEntity.ok(response);
    }
}
