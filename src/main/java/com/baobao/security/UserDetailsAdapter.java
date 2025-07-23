package com.baobao.security;

import com.baobao.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsAdapter implements UserDetails {

    private final User usuario;

    public UserDetailsAdapter(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPermissao().name()));
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

}
