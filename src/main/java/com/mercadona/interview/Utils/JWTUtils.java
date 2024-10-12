package com.mercadona.interview.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {

    // En un caso real tenemos que utilizar un gestor para estas claves y no pasarlas directamente.
    // Por ejemplo --> AWS Secrets Manager. En el caso de este ejercicio lo dejaremos como está ya que se
    // va a probar en un entorno local.
    private final String SECRET_KEY = "m3rC4d0n41nt3rV13W2o24+";
    private final long EXPIRATION_TIME = 86400000; // 1 día

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY);

        return builder.compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        boolean isValid = (extractedUsername.equals(username) && !isTokenExpired(token));
        System.out.println("Token valid: " + isValid); // Log de validación
        return isValid;
    }
}