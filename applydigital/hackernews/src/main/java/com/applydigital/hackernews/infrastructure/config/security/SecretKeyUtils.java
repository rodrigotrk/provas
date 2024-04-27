package com.applydigital.hackernews.infrastructure.config.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretKeyUtils {

    private SecretKey key;

    public SecretKey getKey(){
        if(key == null){
            key = Jwts.SIG.HS256.key().build();
        }
        return key;
    }
}
