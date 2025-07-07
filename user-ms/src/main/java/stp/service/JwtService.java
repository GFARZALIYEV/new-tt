package stp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import stp.model.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private String secretKey = "QnVyYXlhIHV6dW4gYmlyIHRlc3QgbWVzYWppIHlhemlyYW0gdmUgc2FkZWNlIGJ1bnUgeW94bGF5YWNhbQ";


    public Object getClamsByKey(String token, String key) {
        Claims claims = parserToken(token);
       return claims.get(key);

    }

    public Claims parserToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String generateToken(User user){
        return Jwts.builder()
//                .setSubject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("authorities", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)))
                .signWith(getSecretKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

}
