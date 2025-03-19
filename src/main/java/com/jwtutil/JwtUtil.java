package com.jwtutil;

import java.util.Date; // For Date

import org.springframework.stereotype.Component; // For @Component annotation

import io.jsonwebtoken.Jwts; // For Jwts builder and parser
import io.jsonwebtoken.SignatureAlgorithm; // For specifying the signing algorithm

@Component
public class JwtUtil {
    private String SECRET_KEY = "my_secret_key";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /*public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }*/

    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build() // Build the JwtParser
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}