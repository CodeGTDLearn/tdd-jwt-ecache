package com.wallet.security;

import com.wallet.entity.User;
import com.wallet.util.enums.RoleEnum;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class JwtUserFactory {

    //todo: SpringSecurity+Jwt 4 - Captura dados do User e carrega JwtUser
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId() ,
                user.getEmail() ,
                user.getPassword() ,

                //todo: Roles 6.3 - Recebe a Role vinda do DB
                createGrantedAuthorities(user.getRole())
        );
    }

    //todo: Roles 6.3.1 - Converte a Role vinda, em um real GrantedAuthorities
    private static List<GrantedAuthority> createGrantedAuthorities(RoleEnum role) {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //todo: Roles 6.3.2 - Criando as GRantedAuthorities .
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return authorities;
    }

}
