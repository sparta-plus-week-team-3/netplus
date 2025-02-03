package com.example.com.netplus.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    //1. SecretKey 를 생성자에서 초기화
    public JwtUtil(JwtProperties jwtProperties) {
        System.out.println("JwtProperties Loaded: " + jwtProperties.getSecretKey() + ", " + jwtProperties.getExpiration());
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.expiration = jwtProperties.getExpiration();
    }

    //2.jwt 토큰 생성
    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, userId.toString());
    }

    //3. jwt 토큰에서 사용자 id 추출
    public Long extractUserId(String token) {
        return Long.parseLong(extractClaims(token).getSubject());
    }

    // public methods
    public boolean validateToken(String token) {
        try {
            return !extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // private methods
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) //클레임
                .setSubject(subject) //id
                .setIssuedAt(new Date()) //토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) //key와 서명 알고리즘
                .compact();//토큰 생성
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)//서명검증
                .build()
                .parseClaimsJws(token)//jwt 파싱
                .getBody();//클레임 반환
    }


}
