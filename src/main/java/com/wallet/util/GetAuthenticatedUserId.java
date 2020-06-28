package com.wallet.util;

import com.wallet.entity.User;
import com.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

//todo: SpringSecurity+Jwt 9.4 - Captura informacoes do Usuario autenticado
@Component
public class GetAuthenticatedUserId {

    private static UserService staticService;

    public GetAuthenticatedUserId(UserService service) {
        GetAuthenticatedUserId.staticService = service;
    }

    public static Long getAuthenticatedUserId() {

        //todo: SpringSecurity+Jwt 9.4.1 - getName() Referese-se ao EMAIL do usuario autenticado
        try {
            Optional<User> user = staticService
                    .findByEmail(
                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    .getName());

            //todo: SpringSecurity+Jwt 9.4.2 - Usuario encontrado retorna ID dele
            if (user.isPresent()) {
                return user.get().getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
