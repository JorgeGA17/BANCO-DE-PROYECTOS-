package com.sisgestion_back.sigestion_back.service;

import com.sisgestion_back.sigestion_back.Enum.ERole;
import com.sisgestion_back.sigestion_back.exception.ResourceNotFoundException;
import com.sisgestion_back.sigestion_back.mapper.UserMapper;
import com.sisgestion_back.sigestion_back.model.dto.AuthResponseDTO;
import com.sisgestion_back.sigestion_back.model.dto.LoginDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserProfileDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserRegistrationDTO;
import com.sisgestion_back.sigestion_back.model.entity.Personal;
import com.sisgestion_back.sigestion_back.model.entity.Role;
import com.sisgestion_back.sigestion_back.model.entity.User;
import com.sisgestion_back.sigestion_back.repository.PersonalRepository;
import com.sisgestion_back.sigestion_back.repository.RoleRepository;
import com.sisgestion_back.sigestion_back.repository.UserRepository;
import com.sisgestion_back.sigestion_back.security.TokenProvider;
import com.sisgestion_back.sigestion_back.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.sisgestion_back.sigestion_back.Audit.Config.JpaAuditingConfig.AuditContextUtils.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final EmailService emailService;

    @Override
    public UserProfileDTO registerAdmin(UserRegistrationDTO userRegistrationDTO) {
        return registerUserWithRole(userRegistrationDTO, ERole.ADMIN);
    }

    @Override
    public UserProfileDTO registerSecretario(UserRegistrationDTO userRegistrationDTO) {
        return registerUserWithRole(userRegistrationDTO, ERole.SECRETARIO);
    }

    @Override
    public UserProfileDTO registerGeneral(UserRegistrationDTO userRegistrationDTO) {
        return registerUserWithRole(userRegistrationDTO, ERole.GENERAL);
    }

    @Override
    public UserProfileDTO updateUser(long userPk, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(userPk)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // 1. Validar que el nombre y apellido no pertenezcan a OTRO usuario distinto al actual
        boolean existAsPersonal = personalRepository.existsByXnombreAndXapellidoAndUser_UserPkNot(
                userProfileDTO.getXnombre(),
                userProfileDTO.getXapellido(),
                userPk
        );

        if (existAsPersonal) {
            throw new IllegalArgumentException("Ya existe otro usuario con ese nombre y apellido");
        }

        // 2. Validar correo institucional si fue enviado y no pertenece a otro usuario
        if (userProfileDTO.getXcorreoInstitucional() != null) {
            userRepository.findByXcorreoInstitucional(userProfileDTO.getXcorreoInstitucional())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getUserPk().equals(userPk)) {
                            throw new IllegalArgumentException("Ya existe otro usuario con ese correo institucional");
                        }
                    });
            user.setXcorreoInstitucional(userProfileDTO.getXcorreoInstitucional());
        }

        // 3. Actualizar datos de Personal
        if (user.getPersonal() != null) {
            user.getPersonal().setXnombre(userProfileDTO.getXnombre());
            user.getPersonal().setXapellido(userProfileDTO.getXapellido());
            user.getPersonal().setXnombreCompleto(userProfileDTO.getXnombre() + " " + userProfileDTO.getXapellido());
            if (userProfileDTO.getXcorreoInstitucional() != null) {
                user.getPersonal().setXcorreoInstitucional(userProfileDTO.getXcorreoInstitucional());
            }
        }

        // 4. Si se envió una nueva contraseña (y no está en blanco), encriptarla y actualizarla
        if (userProfileDTO.getPassword() != null && !userProfileDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        }

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        user.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        user.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        user.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        user.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        User updateUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(updateUser);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        // autenticacion de usuario con autenticationmanager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getXcorreoInstitucional(), loginDTO.getPassword())
        );

        // una vez autenticado, el objeto de autenticacion contiene la informacion del usuario
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);
        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user, token);

        return responseDTO;
    }

    @Override
    public String sendPasswordRecoveryEmail(String xcorreoInstitucional) {
        User user = userRepository.findByXcorreoInstitucional(xcorreoInstitucional)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró usuario con el correo: " + xcorreoInstitucional));

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordExpiry(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);

        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        String subject = "Recuperación de Contraseña - SisGestión";
        String body = "Hola,\n\n"
                + "Has solicitado restablecer tu contraseña.\n"
                + "Haz clic en el siguiente enlace para crear una nueva contraseña:\n" + resetUrl + "\n\n"
                + "Este enlace tiene una validez de 30 minutos.\n"
                + "Token de recuperación: " + token;

        emailService.sendEmail(user.getXcorreoInstitucional(), subject, body);

        return token;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token de recuperación inválido o inexistente"));

        if (user.getResetPasswordExpiry() == null || user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El token de recuperación ha expirado");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        userRepository.save(user);
    }

    @Override
    public UserProfileDTO getUserById(long userPk) {
       User user = userRepository.findById(userPk)
               .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
       return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerUserWithRole(UserRegistrationDTO userRegistrationDTO, ERole roleEnum) {

        //verificar si el email existe o si hay usuario con nombre y apellido
        boolean existByXcorreoInstitucional = userRepository.existsByXcorreoInstitucional(userRegistrationDTO.getXcorreoInstitucional());
        boolean existAsPersonal = personalRepository.existsByXnombreAndXapellido(userRegistrationDTO.getXnombre(), userRegistrationDTO.getXapellido());

        if (existByXcorreoInstitucional) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        if (existAsPersonal) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre y apellido");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new IllegalArgumentException("Error: Rol no existe"));

        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        User user = userMapper.toUserEntity(userRegistrationDTO);
        user.setRole(role);

        if (roleEnum == ERole.SECRETARIO) {
            Personal personal  = new Personal();
            personal.setXnombre(userRegistrationDTO.getXnombre());
            personal.setXapellido(userRegistrationDTO.getXapellido());
            personal.setNnumeroDocumento(userRegistrationDTO.getNnumeroDocumento());
            personal.setXocupacion(userRegistrationDTO.getXocupacion());
            personal.setXnombreCompleto(userRegistrationDTO.getXnombre()+ " " + userRegistrationDTO.getXapellido());
            personal.setXcorreoInstitucional(userRegistrationDTO.getXcorreoInstitucional());

            personal.setUser(user);
            user.setPersonal(personal);

        } else if (roleEnum == ERole.GENERAL) {
            Personal personal  = new Personal();
            personal.setXnombre(userRegistrationDTO.getXnombre());
            personal.setXapellido(userRegistrationDTO.getXapellido());
            personal.setNnumeroDocumento(userRegistrationDTO.getNnumeroDocumento());
            personal.setXocupacion(userRegistrationDTO.getXocupacion());
            personal.setXnombreCompleto(userRegistrationDTO.getXnombre()+ " " + userRegistrationDTO.getXapellido());
            personal.setXcorreoInstitucional(userRegistrationDTO.getXcorreoInstitucional());
            personal.setUser(user);
            user.setPersonal(personal);
        }
        else if (roleEnum == ERole.ADMIN) {
            Personal personal  = new Personal();
            personal.setXnombre(userRegistrationDTO.getXnombre());
            personal.setXapellido(userRegistrationDTO.getXapellido());
            personal.setNnumeroDocumento(userRegistrationDTO.getNnumeroDocumento());
            personal.setXocupacion(userRegistrationDTO.getXocupacion());
            personal.setXnombreCompleto(userRegistrationDTO.getXnombre()+ " " + userRegistrationDTO.getXapellido());
            personal.setXcorreoInstitucional(userRegistrationDTO.getXcorreoInstitucional());
            personal.setUser(user);
            user.setPersonal(personal);
        }

        // Llenar los campos de auditoría que requieren información del contexto de la solicitud
        user.setCAudUidred("usuario_red_ejemplo"); // Aquí deberías obtenerlo del contexto o DTO
        user.setCAudPc(getClientHostname()); // Obtiene el nombre del host (del servidor o del proxy, no cliente)
        user.setNAudIp(getClientIpAddress()); // Obtiene la IP del cliente (si está detrás de proxy X-Forwarded-For)
        user.setCAudMcaddr(getClientMacAddress()); // Muy difícil de obtener del cliente de forma fiable

        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }
}
