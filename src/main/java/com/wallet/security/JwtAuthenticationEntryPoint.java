package com.wallet.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //todo: SpringSecurity+Jwt 2 - Customiza mensagem de Negativa de Acesso, devido JWT invalido
    @Override
    public void commence(
            HttpServletRequest request ,
            HttpServletResponse response ,
            AuthenticationException authException)
            throws IOException {

        response
                .sendError(HttpServletResponse.SC_UNAUTHORIZED ,
                        "Acesso negado. " +
                                "Você deve estar autenticado" +
                                " no sistema para acessar a URL solicitada.");
    }

}
