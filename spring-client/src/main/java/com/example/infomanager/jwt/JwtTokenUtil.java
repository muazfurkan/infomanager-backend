package com.example.infomanager.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "<REMOVED>";


    public String generateToken(String username, String password){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 10000))
                .setIssuedAt(new Date())
                .claim("password", password)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //veritabanıyla bir karşılaştırma yapılarak doğrulanması yapılacak. uyuyom!!!!

    public Claims getClaims(String token){
        Jwt<Header, Claims> jwt = Jwts.parserBuilder().build().parseClaimsJwt(token);
        return jwt.getBody();
    }

    public String getAdminName(Claims claims){
        return claims.getSubject();
    }

    public Date getDate(Claims claims){
        return claims.getExpiration();
    }

}
