package com.wallet.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//todo: SpringSecurity+Jwt 8.3 - injeta JwtTokenUtil - Validacao dos tokens
@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";


    //todo: SpringSecurity+Jwt 8.3.1 - chave aleatoria p/ geracao dos tolkens
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {

        String username;

        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


    public boolean validToken(String token) {
        return !expiredToken(token);
    }


    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME ,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED ,new Date());

        //todo: Roles 6.4 - Seta as Roles dentro do token .
        userDetails
                .getAuthorities()
                .forEach(authority ->
        //todo: Roles 6.4.1 - Seta as Key[CLAIM_KEY_ROLE] e inserir a role via authority.getAuthority() .
                        claims.put(CLAIM_KEY_ROLE ,authority.getAuthority())
                );

        return generateToken(claims);
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    private boolean expiredToken(String token) {
        Date expirationDate = this.getExpirationDateFromToken(token);
        if (expirationDate == null) {
            return false;
        }
        return expirationDate.before(new Date());
    }


    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512 ,secret)
                .compact();
    }

}