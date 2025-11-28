package com.exercicio.gerenciamento_de_carros.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    //Chave Secreta
    private static final SecretKey key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("Qm9sZUVhc3RlckZlbml4IUAyMDI1XzNqRk1mUzhxJTNkUXF6RnY1RzRxbUFXcWpOUCU0MWhzR0E0")
    );

    private static final long EXPIRATION = 86400000;

    // Gerar token com apenas o ID
    public String gerarToken(UUID id, String email, String nome) {
        return Jwts.builder()
                .subject(email)
                .claim("id", id.toString())
                .claim("nome", nome)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key) // API nova — sem algoritmo explícito
                .compact();
    }

    // Validar token e devolver os Claims
    public boolean isTokenValid(String token, String username) {
        String extracted = extractUsername(token);
        return extracted != null && extracted.equals(username) && !isExpired(token);
    }

    //Extrai o username do token JWT
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //Descriptografiza o token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extrair ID do token
    public UUID extrairId(String token) {
        return UUID.fromString(extractAllClaims(token).getSubject());
    }

    // Verifica expiração
    private boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

