package com.wallet.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//todo: SpringSecurity+Jwt 5 - CArrega c/ User o JwtUser - implementa UserDetails
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = -268046329085485932L;

    private Long id;
    private String username;
    private String password;

    //todo: Roles 6 - Armazena nossas Roles/Perfis de acesso
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id ,
            String username ,
            String password ,
    //todo: Roles 6.1 - Armazena nossas Roles/Perfis de acesso, no construtor
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    //todo: SpringSecurity+Jwt 5.1 - Seta TRUE em: isEnabled/AccNoExpir/NoLock/CredNoExpir
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    //todo: Roles 6.2 - Getta as Authorities (perfis de acesso)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
