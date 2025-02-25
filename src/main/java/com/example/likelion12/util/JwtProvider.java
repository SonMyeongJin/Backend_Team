package com.example.likelion12.util;

import com.example.likelion12.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.NoSuchElementException;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    // Access Token 생성
    public String createAccessToken(String email, Long memberId) {
        Claims claims = Jwts.claims().setSubject(email.trim());
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("memberId", memberId)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Access Token 검증
    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰에서 사용자 정보 추출
    public Long extractIdFromHeader(String authorization) {
        // Authorization 헤더에서 JWT 토큰 추출
        String jwtToken;
        try {
            jwtToken = extractJwtToken(authorization);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 헤더 포맷입니다.");
        }

        // JWT 토큰에서 사용자 정보 추출
        Long memberId;
        try {
            memberId = extractMemberIdFromJwtToken(jwtToken);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("토큰에서 멤버 아이디를 찾을 수 없습니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 토큰 형식입니다.");
        }

        return memberId;
    }

    public String extractJwtToken(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2) {
            return parts[1].trim(); // 토큰 부분 추출 및 공백 제거
        }
        throw new IllegalArgumentException("유효하지 않은 헤더 포맷입니다.");
    }

    public Long extractMemberIdFromJwtToken(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        Long memberId = claims.get("memberId", Long.class);

        if (memberId == null) {
            throw new NoSuchElementException("토큰에서 멤버 아이디를 찾을 수 없습니다.");
        }

        return memberId;
    }
}
