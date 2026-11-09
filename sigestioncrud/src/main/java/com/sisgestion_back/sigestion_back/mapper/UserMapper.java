package com.sisgestion_back.sigestion_back.mapper;

import com.sisgestion_back.sigestion_back.model.dto.AuthResponseDTO;
import com.sisgestion_back.sigestion_back.model.dto.LoginDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserProfileDTO;
import com.sisgestion_back.sigestion_back.model.dto.UserRegistrationDTO;
import com.sisgestion_back.sigestion_back.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toUserEntity(UserRegistrationDTO userRegistrationDTO) {
        return modelMapper.map(userRegistrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        if (user.getPersonal() != null) {
            userProfileDTO.setXnombre(user.getPersonal().getXnombre());
            userProfileDTO.setXapellido(user.getPersonal().getXapellido());
        }

        return userProfileDTO;
    }


    //convertir LoginDTO a User(procesamiento login)
    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    // Convertir User a AuthResponseDTO para la respuesta de autenticacion
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        //obtener nombre y apellido
        String xnombre = (user.getPersonal()!=null) ? user.getPersonal().getXnombre()
                : "Admin";

        String xapellido = (user.getPersonal() != null) ? user.getPersonal().getXapellido()
                : "User";

        authResponseDTO.setXnombre(xnombre);
        authResponseDTO.setXapellido(xapellido);

        authResponseDTO.setRole(user.getRole().getName().name());

        return authResponseDTO;

    }

}
