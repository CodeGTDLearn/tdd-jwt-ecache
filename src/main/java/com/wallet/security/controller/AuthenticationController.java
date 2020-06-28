package com.wallet.security.controller;

import com.wallet.response.Response;
import com.wallet.security.dto.JwtAuthenticationDTO;
import com.wallet.security.dto.TokenDTO;
import com.wallet.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//todo: SpringSecurity+Jwt 8.6 - EndPoint GERADOR  dos tolkens
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    //todo: SpringSecurity+Jwt 8.6.1 - Metodo GERADOR  dos tolkens
    @PostMapping
    public ResponseEntity<Response<TokenDTO>> gerarTokenJwt(
            @Valid @RequestBody JwtAuthenticationDTO jwtAuthDto ,
            BindingResult result)
            throws AuthenticationException {
        //todo: SpringSecurity+Jwt 8.6.2 - TolkenDTO payload
        Response<TokenDTO> response = new Response<>();

        //todo: SpringSecurity+Jwt 8.6.4 - Checa erros no BindingResult
        if (result.hasErrors()) {
            result.getAllErrors()
                    .forEach(
                            error -> response.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        //todo: SpringSecurity+Jwt 8.6.5 - Checa usuario de acordom com email+password
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                jwtAuthDto.getEmail() ,
                                jwtAuthDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails =
                userDetailsService
                        .loadUserByUsername(jwtAuthDto.getEmail());

        //todo: SpringSecurity+Jwt 8.6.6 - Gera Tolken(jwtTokenUtil), pois nao houve erro algum ate aqui
        String token = jwtTokenUtil.getToken(userDetails);

        //todo: SpringSecurity+Jwt 8.6.7 - Passa/devolve o token na resposta
        response.setData(new TokenDTO(token));
        return ResponseEntity.ok(response);
    }
}