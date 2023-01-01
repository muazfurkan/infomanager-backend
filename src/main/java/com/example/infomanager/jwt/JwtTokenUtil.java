package com.example.infomanager.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "<REMOVED>";


    public String generateToken(String username, String password){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(username)
                .claim("password", password)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
