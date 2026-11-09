package com.sisgestion_back.sigestion_back.security;

import com.sisgestion_back.sigestion_back.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;
    private String XcorreoInstitucional;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private User user;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return XcorreoInstitucional;
    }
}
