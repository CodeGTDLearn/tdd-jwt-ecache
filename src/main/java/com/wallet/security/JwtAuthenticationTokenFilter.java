package com.wallet.security;

import com.wallet.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//todo: SpringSecurity+Jwt 8.1 - extends OncePerRequestFilter
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private UserDetailsService userDetailsService;

    //todo: SpringSecurity+Jwt 8.2 - injeta jwtTokenUtil
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //todo: SpringSecurity+Jwt 8.4 - doFilterInternal - PAsso-a-Passo explicando nos comments
    @Override
    protected void doFilterInternal(
            HttpServletRequest request ,
            HttpServletResponse response ,
            FilterChain chain)
            throws ServletException, IOException {

        // 1 - GETTA A key 'AUTHORIZATION' DO HEADER
        String token = request.getHeader(AUTH_HEADER);

        // 2 - CHECA SE A key 'AUTHORIZATION' E NULA OU INICIA COM 'BEARER'
        if (token != null && token.startsWith(BEARER_PREFIX)) {

            // 3 - EXTRAI O TOKEN, EXCLUINDO A PALAVRA 'BEARER'
            token = token.substring(7);
        }

        // 4 - PARSEIA O JWT, CAPTURANDO O 'USERNAME'
        String username = jwtTokenUtil.getUsernameFromToken(token);

        // 5 - AVANCA SE:
        // a) O 'USERNAME' NAO FOR NULO;
        // b) SE 'JA' NAO EXISTE CONTEXTO-DE-AUTENTICACAO
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6 - BUSCA O OBJETO USUARIO - PARTINdO DO USERNAME ENCONTRADO NO TOKEN
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 7 - CHECA EXPIRACAO/EXISTENCIA DO TOKEN
            if (jwtTokenUtil.validToken(token)) {

                // 8 - SETA CONTEXTO-DE-AUTENTICACAO, COMO AUTENTICADO
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails ,
                                null ,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 9 - REQUEST PROSSEGUE AUTENTICADO, NO CONTEXTO-DE-AUTENTICACAO
        chain.doFilter(request ,response);
    }
}
