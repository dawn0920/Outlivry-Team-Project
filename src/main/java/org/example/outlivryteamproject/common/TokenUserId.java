package org.example.outlivryteamproject.common;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.config.JwtUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUserId {

    private final JwtUtil jwtUtil;

    public Long getTokenUserId(String authHeader) {

        String token = jwtUtil.substringToken(authHeader);
        Claims claims = jwtUtil.extractClaims(token);
        Long userId = Long.parseLong(claims.getSubject());

        return userId;
    }
}
