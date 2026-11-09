package com.sisgestion_back.sigestion_back.security;

import com.sisgestion_back.sigestion_back.model.entity.User;
import com.sisgestion_back.sigestion_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class PersonalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String XcorreoInstitucional) throws UsernameNotFoundException {
        User user = userRepository.findByXcorreoInstitucional(XcorreoInstitucional)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: "+XcorreoInstitucional));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name());

        return new UserPrincipal(
                user.getUserPk(),
                user.getXcorreoInstitucional(),
                user.getPassword(),
                Collections.singletonList(authority),
                user
        );
    }
}
