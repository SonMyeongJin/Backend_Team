package com.example.likelion12.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    private final RedisTemplate<String, Object> redisTemplate;

    public void storeToken(String token, Long memberId) {
        // redis 에 토큰 저장
        log.info("[TokenService.storeToken]");
        redisTemplate.opsForValue().set(String.valueOf(memberId), token, accessTokenExpiration, TimeUnit.MILLISECONDS);
    }

    public boolean checkTokenExists(String token) {
        // 들어온 토큰이 redis 에 있는지 확인
        Boolean result = redisTemplate.hasKey(token);
        return result != null && result;
    }

    public void invalidateToken(Long memberId) {
        // redis 에서 토큰 삭제
        redisTemplate.delete(String.valueOf(memberId));
    }
}
