package com.applydigital.hackernews.infrastructure.security;

import com.applydigital.hackernews.infrastructure.security.model.AuthenticateResponse;
import com.applydigital.hackernews.infrastructure.users.persistence.entity.UserJpaEntity;
import com.applydigital.hackernews.infrastructure.config.security.SecretKeyUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKeyUtils keyGenerator;

    public JwtService(SecretKeyUtils keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public AuthenticateResponse generateToken(UserJpaEntity user){

        String token = Jwts
                .builder()
                .signWith(keyGenerator.getKey())
                .subject(user.getEmail())
                .expiration(generateExpirationDate())
                .claims(generateTokenClaims(user))
                .compact();

        return new AuthenticateResponse(token);
    }

    private Date generateExpirationDate(){
        var expirationMinutes = 60;
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(UserJpaEntity user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("name", user.getName());
        return claims;
    }

    public String getEmailFromToken(String tokenJwt){

        try{
            return Jwts.parser()
                    .verifyWith(keyGenerator.getKey())
                    .build()
                    .parseSignedClaims(tokenJwt)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e){
            throw new RuntimeException(e.getMessage());
        }


    }
}
